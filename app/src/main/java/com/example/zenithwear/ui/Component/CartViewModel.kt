package com.example.zenithwear.ui.Component

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.zenithwear.data.Model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    // Lista de productos en el carrito
    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts.asStateFlow()


    fun addProductToCart(product: Product) {
        _cartProducts.update { currentList ->
            if (currentList.none { it.id == product.id }) {
                currentList + product
            } else {
                currentList
            }
        }
    }

    fun removeProductFromCart(product: Product) {
        _cartProducts.update { currentList ->
            currentList.filterNot { it.id == product.id }
        }
    }



    private val _favoriteProducts = mutableStateListOf<Product>()
    val favoriteProducts: List<Product> get() = _favoriteProducts

    fun clearCart() {
        _cartProducts.value = emptyList() // Limpia el carrito
    }
    // Agregar producto a favoritos
    fun addProductToFavorites(product: Product) {
        if (!_favoriteProducts.contains(product)) {
            _favoriteProducts.add(product)
        }
    }

    // Eliminar producto de favoritos
    fun removeProductFromFavorites(product: Product) {
        _favoriteProducts.remove(product)
    }

    // Verificar si un producto está en favoritos
    fun isProductInFavorites(product: Product): Boolean {
        return _favoriteProducts.contains(product)
    }

    // Alternar estado favorito (agregar o eliminar)
    fun toggleFavorite(product: Product) {
        if (isProductInFavorites(product)) {
            removeProductFromFavorites(product)
        } else {
            addProductToFavorites(product)
        }
    }

}
