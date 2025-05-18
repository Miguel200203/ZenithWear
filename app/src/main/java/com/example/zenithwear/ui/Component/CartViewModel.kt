package com.example.zenithwear.ui.Component

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.zenithwear.data.Model.Product

class CartViewModel : ViewModel() {
    // Lista de productos en el carrito
    private val _cartProducts = mutableStateListOf<Product>()
    val cartProducts: List<Product> get() = _cartProducts

    // Lista de productos favoritos
    private val _favoriteProducts = mutableStateListOf<Product>()
    val favoriteProducts: List<Product> get() = _favoriteProducts

    // Agregar producto al carrito
    fun addProductToCart(product: Product) {
        if (!_cartProducts.contains(product)) {
            _cartProducts.add(product)
        }
    }

    // Eliminar producto del carrito
    fun removeProductFromCart(product: Product) {
        _cartProducts.remove(product)
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
    fun clearCart() {
        _cartProducts.clear()
    }
}