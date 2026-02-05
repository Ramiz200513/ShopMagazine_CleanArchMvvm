package com.example.shopmagazine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.model.CartWithProduct
import com.example.shopmagazine.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class CartState(
    val items: List<CartWithProduct> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false
)
@HiltViewModel
class CartWithProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel(){
    val state: StateFlow<CartState> = combine(
        repository.getCart(),
        repository.getCartTotalPrice()
        ){items,total->
        CartState(
            items = items,
            total
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartState(isLoading = true)
    )
    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            repository.addToCart(productId)
        }
    }

    fun decreaseQuantity(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }
    fun deleteItem(item: CartItemEntity) {
        viewModelScope.launch {
            repository.deleteCartItem(item)
        }
    }
    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}