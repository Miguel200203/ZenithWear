package com.example.zenithwear.data.Model

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val image: Int,
    val category: Int,
    val brand: Int?,
    val description: String,
    val size: String,
    val available: Int
)