package com.example.shopmagazine.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.model.CartWithProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cart_items")
    fun getCart(): Flow<List<CartWithProduct>>
    @Query("Select * from cart_items where productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItem(cartItem: CartItemEntity): Long
    @Query("Update cart_items set quantity = :newQuantity where id= :itemId")
    suspend fun updateQuantity(itemId:Int,newQuantity:Int)

    @Transaction
    suspend fun addToCartOrIncrement(productId: Int){
        val existingItem = getCartItemByProductId(productId)
        if(existingItem!=null){
            updateQuantity(existingItem.id,existingItem.quantity + 1)
        }else {
            val newItem = CartItemEntity(productId = productId, quantity = 1)
            insertCartItem(newItem)
        }
    }
    @Query("SELECT COALESCE(SUM(p.price * c.quantity), 0) FROM cart_items as c Inner Join products as p on c.productId = p.id")
    fun getCartTotalPriceFlow(): Flow<Double?>
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)
    @Transaction
    suspend fun decrementOrRemove(cartItem: CartItemEntity) {
        if (cartItem.quantity > 1) {
            updateQuantity(cartItem.id, cartItem.quantity - 1)
        } else {
            deleteCartItem(cartItem)
        }
    }
}