package com.example.shopmagazine.data.repository

import com.example.shopmagazine.data.local.dao.CartDao
import com.example.shopmagazine.data.local.dao.ProductDao
import com.example.shopmagazine.data.network.ProductsApi
import com.example.shopmagazine.data.remote.dto.ProductDto
import com.example.shopmagazine.data.remote.dto.RatingDto
import com.example.shopmagazine.presentation.screen.screen.ProductItem
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException

class ProductRepositoryImplTest {
    private val dao: ProductDao = mockk()
    private val api: ProductsApi = mockk(relaxed = true)
    private val cartDao: CartDao = mockk()
    private lateinit var repository: ProductRepositoryImpl
    @Before
    fun setup(){
        repository = ProductRepositoryImpl(dao = dao, api = api, cartDao = cartDao)
    }
    @Test
    fun `refreshProducts should fetch from API and save to DAO`() = runTest {
        val fakeApiData = listOf(
            ProductDto(
                id = 1,
                title = "Test Product",
                price = 100.0,
                description = "Desc",
                category = "Cat",
                image = "url",
                rating = RatingDto(4.5, 10)
            )
        )
        coEvery { api.getAllProducts() } returns fakeApiData
        repository.refreshProducts()
        coVerify(exactly = 1) { dao.replaceAll(any()) }
    }
    @Test
    fun`refreshProducts should handle API error gracefully`() = runTest {
        coEvery { api.getAllProducts() } throws RuntimeException("NetworkError")
        var exceptionThrown = false
        try {
            repository.refreshProducts()
        }catch (e: Exception){
            exceptionThrown = true
        }
        assertThat(exceptionThrown).isTrue()
        coVerify(exactly = 0) { dao.replaceAll(any()) }
    }
}