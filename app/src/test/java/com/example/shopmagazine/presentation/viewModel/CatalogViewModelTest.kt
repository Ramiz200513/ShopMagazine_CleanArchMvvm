package com.example.shopmagazine.presentation.viewModel

import com.example.shopmagazine.MainDispatcherRule
import com.example.shopmagazine.data.local.entities.ProductEntity
import com.example.shopmagazine.data.local.entities.RatingEntity
import com.example.shopmagazine.domain.repository.ProductRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CatalogViewModelTest{
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository: ProductRepository = mockk(relaxed = true)
    private lateinit var viewModel: CatalogViewModel
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `viewModel init should load products from repo`()= runTest {
        val testProduct = ProductEntity(
            id = 1, title = "Test", price = 10.0, description = "Desc",
            category = "Cat", image = "Img", rating = RatingEntity(5.0, 1)
        )
        coEvery { repository.getProducts() } returns flowOf(listOf(testProduct))
        viewModel = CatalogViewModel(repository)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect{}
        }
        val state = viewModel.state.value
        assertThat(state.products).contains(testProduct)
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
    }
    @Test
     fun `addToCart should call repository`() = runTest {
     coEvery { repository.getProducts() } returns flowOf(emptyList())
        viewModel = CatalogViewModel(repository)
        val testProduct = ProductEntity(
            id = 99, title = "Phone", price = 999.0, description = "",
            category = "", image = "", rating = RatingEntity(0.0, 0)
        )
        viewModel.addToCart(testProduct)
        io.mockk.coVerify { repository.addToCart(99) }

    }
}