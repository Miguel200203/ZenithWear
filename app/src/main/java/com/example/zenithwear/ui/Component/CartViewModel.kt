package com.example.zenithwear.ui.Component

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.zenithwear.data.Model.ModelProduct

class CartViewModel : ViewModel() {
    // Lista de productos en el carrito
    private val _cartProducts = mutableStateListOf<ModelProduct>()
    val cartProducts: List<ModelProduct> get() = _cartProducts

    // Lista de productos favoritos
    private val _favoriteProducts = mutableStateListOf<ModelProduct>()
    val favoriteProducts: List<ModelProduct> get() = _favoriteProducts

    // Agregar producto al carrito
    fun addProductToCart(product: ModelProduct) {
        if (!_cartProducts.contains(product)) {
            _cartProducts.add(product)
        }
    }

    // Eliminar producto del carrito
    fun removeProductFromCart(product: ModelProduct) {
        _cartProducts.remove(product)
    }

    // Agregar producto a favoritos
    fun addProductToFavorites(product: ModelProduct) {
        if (!_favoriteProducts.contains(product)) {
            _favoriteProducts.add(product)
        }
    }

    // Eliminar producto de favoritos
    fun removeProductFromFavorites(product: ModelProduct) {
        _favoriteProducts.remove(product)
    }

    // Verificar si un producto está en favoritos
    fun isProductInFavorites(product: ModelProduct): Boolean {
        return _favoriteProducts.contains(product)
    }
    fun clearCart() {
        _cartProducts.clear()
    }
}