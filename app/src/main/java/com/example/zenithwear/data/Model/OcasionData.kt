package com.example.zenithwear.data.Model


object OcasionesData {
    val ocasionesRecomendaciones = mapOf(
        "Trabajo" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Men's" || it.categoria == "Sportwear" },
            ProductsData.allProducts.filter { it.categoria == "Collections" || it.categoria == "Footwear" }
        ),
        "Fiesta" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Accessories" || it.categoria == "Footwear" },
            ProductsData.allProducts.filter { it.categoria == "Men's" || it.categoria == "Women's" }
        ),
        "Cita" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Women's" || it.categoria == "Footwear" },
            ProductsData.allProducts.filter { it.categoria == "Men's" || it.categoria == "Collections" }
        ),
        "Deporte" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Sportwear" || it.categoria == "Footwear" },
            ProductsData.allProducts.filter { it.categoria == "Accessories" || it.categoria == "Kids" }
        ),
        "Viaje" to listOf(
            ProductsData.allProducts.filter { it.categoria == "Accessories" || it.categoria == "Footwear" },
            ProductsData.allProducts.filter { it.categoria == "Collections" || it.categoria == "Sportwear" }
        )
    )
}
