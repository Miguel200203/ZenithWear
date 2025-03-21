package com.example.zenithwear.data.Model

enum class Talla(val descripcion: String) {
    XS("Extra Small"),
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large")
}

data class ModelProduct(
    val id:Int,
    val title:String,
    val precio:Number,
    val image:Int,
    val categoria: String,
    val marca: String,
    val descripcion: String,
    val talla: Talla,
    val disponibles: Int
)