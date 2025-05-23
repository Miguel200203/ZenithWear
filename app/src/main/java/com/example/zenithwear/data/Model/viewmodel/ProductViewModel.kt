package com.example.zenithwear.data.Model.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getProducts()
                if (response.isSuccessful) {
                    _products.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }
}
