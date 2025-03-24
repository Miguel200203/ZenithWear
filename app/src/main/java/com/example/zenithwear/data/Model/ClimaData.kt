package com.example.zenithwear.data.Model

object ClimaData {
    val climaRecomendaciones = mapOf(
        "Soleado" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Footwear" || it.categoria == "Accessories" },
            ProductsData.allProducts.filter { it.categoria == "Men's" || it.categoria == "Women's" }
        ),
        "Lluvia" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Footwear" || it.categoria == "Men's" },
            ProductsData.allProducts.filter { it.categoria == "Sportwear" || it.categoria == "Kids" }
        ),
        "Frío" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Collections" || it.categoria == "Footwear" },
            ProductsData.allProducts.filter { it.categoria == "Sportwear" || it.categoria == "Accessories" }
        )
    )
}
