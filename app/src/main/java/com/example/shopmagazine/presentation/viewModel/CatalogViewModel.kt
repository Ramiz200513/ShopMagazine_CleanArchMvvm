package com.example.shopmagazine.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmagazine.data.local.entities.ProductEntity
import com.example.shopmagazine.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CatalogState(
    val products: List<ProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val allCategories: List<String> = emptyList(),
    val selectedCategories: Set<String> = emptySet(),
    val searchQuery: String = "",
    val selectedRating: Int? = null,
    val priceSortOrder: CatalogViewModel.PriceSortOrder = CatalogViewModel.PriceSortOrder.NONE
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    enum class PriceSortOrder {
        NONE,
        ASCENDING,
        DESCENDING
    }
    private val _priceSortOrder = MutableStateFlow(PriceSortOrder.NONE)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    private val _selectedRating = MutableStateFlow<Int?>(null)


    private val filters = combine(
        _searchQuery,
        _selectedCategories,
        _selectedRating,
        _priceSortOrder // Добавляем сюда enum
    ) { query, categories, rating, sortOrder ->
        FilterParams(query, categories, rating, sortOrder)
    }

    val state: StateFlow<CatalogState> = combine(
        repository.getProducts(),
        _isLoading,
        _error,
        filters
    ) { products, isLoading, error, params ->

        // 1. Фильтрация
        var filteredProducts = products.filter { product ->
            val matchesSearch = product.title.contains(params.query, ignoreCase = true)
            val matchesCategory = params.categories.isEmpty() || product.category in params.categories
            val matchesRating = params.rating == null || product.rating.rate >= params.rating.toDouble()
            matchesSearch && matchesCategory && matchesRating
        }

        // 2. Логика сортировки (3 состояния)
        filteredProducts = when (params.sortOrder) {
            PriceSortOrder.ASCENDING -> filteredProducts.sortedBy { it.price }
            PriceSortOrder.DESCENDING -> filteredProducts.sortedByDescending { it.price }
            PriceSortOrder.NONE -> filteredProducts // Оставляем как есть
        }

        CatalogState(
            products = filteredProducts,
            isLoading = isLoading,
            error = error,
            allCategories = products.map { it.category }.distinct(),
            selectedCategories = params.categories,
            searchQuery = params.query,
            selectedRating = params.rating,
            priceSortOrder = params.sortOrder // Передаем состояние в UI
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CatalogState(isLoading = true))

    fun toggleSortByPrice() {
        _priceSortOrder.value = when (_priceSortOrder.value) {
            PriceSortOrder.NONE -> PriceSortOrder.ASCENDING
            PriceSortOrder.ASCENDING -> PriceSortOrder.DESCENDING
            PriceSortOrder.DESCENDING -> PriceSortOrder.NONE
        }
    }

    // Вспомогательный класс для передачи параметров
    data class FilterParams(
        val query: String,
        val categories: Set<String>,
        val rating: Int?,
        val sortOrder: PriceSortOrder
    )

    // ... остальные функции (loadProducts, toggleCategory и т.д. остаются без изменений)
    init { loadProducts() }

    fun toggleCategory(category: String) {
        val current = _selectedCategories.value
        _selectedCategories.value = if (current.contains(category)) current - category else current + category
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try { repository.refreshProducts() }
            catch (e: Exception) { _error.value = "Ошибка: ${e.localizedMessage}" }
            finally { _isLoading.value = false }
        }
    }

    fun onSearchQueryChanged(query: String) { _searchQuery.value = query }

    fun onRatingSelected(rating: Int?) {
        _selectedRating.value = if (_selectedRating.value == rating) null else rating
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch { repository.addToCart(product.id) }
    }
}