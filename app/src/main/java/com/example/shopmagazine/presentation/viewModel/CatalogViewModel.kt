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
    val selectedRating: Int? = null
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    private val _selectedRating = MutableStateFlow<Int?>(null)
    private val filters = combine(
        _searchQuery,
        _selectedCategories,
        _selectedRating
    ) { query, categories, rating ->
        Triple(query, categories, rating)
    }

    val state: StateFlow<CatalogState> = combine(
        repository.getProducts(),
        _isLoading,
        _error,
        filters
    ) { products, isLoading, error, (query, categories, rating) ->

        val filteredProducts = products.filter { product ->
            val matchesSearch = product.title.contains(query, ignoreCase = true)

            // Логика мультивыбора:
            // Если набор пуст -> показываем всё.
            // Иначе -> проверяем, содержится ли категория продукта в выбранном наборе.
            val matchesCategory = categories.isEmpty() || product.category in categories

            val matchesRating = rating == null || product.rating.rate >= rating.toDouble()

            matchesSearch && matchesCategory && matchesRating
        }

        CatalogState(
            products = filteredProducts,
            isLoading = isLoading,
            error = error,
            allCategories = products.map { it.category }.distinct(),
            selectedCategories = categories, // Обновляем State
            searchQuery = query,
            selectedRating = rating
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CatalogState(isLoading = true))

    init {
        loadProducts()
    }
    fun toggleCategory(category: String) {
        val current = _selectedCategories.value
        if (current.contains(category)) {
            _selectedCategories.value = current - category // Убрать, если есть
        } else {
            _selectedCategories.value = current + category // Добавить, если нет
        }
    }
    fun clearCategories() {
        _selectedCategories.value = emptySet()
    }
    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshProducts()
            } catch (e: Exception) {
                _error.value = "Ошибка обновления: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            repository.addToCart(product.id)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }



    fun onRatingSelected(rating: Int?) {
        _selectedRating.value =
            if (_selectedRating.value == rating) null else rating
    }
}
