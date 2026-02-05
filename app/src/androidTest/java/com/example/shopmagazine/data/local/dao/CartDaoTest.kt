package com.example.shopmagazine.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shopmagazine.data.local.ShopDatabase
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CartDaoTest {
    private lateinit var database: ShopDatabase
    private lateinit var dao: CartDao
    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShopDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.cartDao()
    }
    @After
    @Throws(IOException::class)
    fun teardown(){
        database.close()
    }
    @Test
    fun insertCartItem() = runBlocking {
        val cartItem = CartItemEntity(id = 1,101,2)
        dao.insertCartItem(cartItem)
        val cartList = dao.getCart().first()
        assertThat(cartList.map{it.cartItem}.contains(cartItem))
    }


}