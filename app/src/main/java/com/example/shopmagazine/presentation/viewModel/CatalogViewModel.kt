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
    val selectedSort: CatalogViewModel.SortOption = CatalogViewModel.SortOption.NONE
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    enum class SortOption(val title: String) {
        NONE("По умолчанию"),
        PRICE_ASC("Дешевле"),
        PRICE_DESC("Дороже"),
        ALPHABET("По алфавиту (А-Я)"),
        RATING("По рейтингу")
    }

    private val _selectedSort = MutableStateFlow(SortOption.NONE)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    private val _selectedRating = MutableStateFlow<Int?>(null)

    // Вспомогательный класс для передачи параметров
    // Исправлено: типы параметров должны соответствовать SortOption
    private data class FilterParams(
        val query: String,
        val categories: Set<String>,
        val rating: Int?,
        val sortOption: SortOption
    )

    private val filters = combine(
        _searchQuery,
        _selectedCategories,
        _selectedRating,
        _selectedSort
    ) { query, categories, rating, sort ->
        FilterParams(query, categories, rating, sort)
    }

    val state: StateFlow<CatalogState> = combine(
        repository.getProducts(),
        _isLoading,
        _error,
        filters
    ) { products, isLoading, error, params ->

        // 1. Фильтрация
        val filteredProducts = products.filter { product ->
            val matchesSearch = product.title.contains(params.query, ignoreCase = true)
            val matchesCategory = params.categories.isEmpty() || product.category in params.categories
            val matchesRating = params.rating == null || product.rating.rate >= params.rating.toDouble()

            matchesSearch && matchesCategory && matchesRating
        }

        // 2. Сортировка
        // Исправлено: обращаемся к params.sortOption (как в data class выше)
        val sortedProducts = when (params.sortOption) {
            SortOption.PRICE_ASC -> filteredProducts.sortedBy { it.price }
            SortOption.PRICE_DESC -> filteredProducts.sortedByDescending { it.price }
            SortOption.ALPHABET -> filteredProducts.sortedBy { it.title }
            SortOption.RATING -> filteredProducts.sortedByDescending { it.rating.rate }
            SortOption.NONE -> filteredProducts
        }

        CatalogState(
            products = sortedProducts,
            isLoading = isLoading,
            error = error,
            allCategories = products.map { it.category }.distinct(),
            selectedCategories = params.categories,
            searchQuery = params.query,
            selectedRating = params.rating,
            selectedSort = params.sortOption
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CatalogState(isLoading = true)
    )

    init {
        loadProducts()
    }

    // Добавлена функция для смены сортировки
    fun onSortOptionSelected(option: SortOption) {
        _selectedSort.value = option
    }

    fun toggleCategory(category: String) {
        val current = _selectedCategories.value
        _selectedCategories.value = if (current.contains(category)) current - category else current + category
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshProducts()
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onRatingSelected(rating: Int?) {
        _selectedRating.value = if (_selectedRating.value == rating) null else rating
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            repository.addToCart(product.id)
        }
    }
}