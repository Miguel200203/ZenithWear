package com.example.zenithwear.data.Model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuModel (
    val id:Int,
    val title:String,
    val option:String,
    val categoria: String,
    val icon: ImageVector,
    val subItems: List<MenuModel> = emptyList()

)