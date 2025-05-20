package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.ui.Component.CartViewModel

@Composable
fun Favorite(navHostController: NavHostController, cartViewModel: CartViewModel) {
   /* Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            if (cartViewModel.favoriteProducts.isNotEmpty()) {
                items(
                    items = cartViewModel.favoriteProducts,
                    key = { product -> product.id }
                ) { product ->
                    ProductItem(
                        product = product,
                        onClick = {
                            try {
                                // Navega a la descripción del producto
                                navHostController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                                navHostController.navigate("productDetail")
                            } catch (e: Exception) {
                                // Log the exception or handle it appropriately
                                e.printStackTrace()
                            }
                        },
                        onRemove = {
                            // Elimina el producto de favoritos
                            cartViewModel.removeProductFromFavorites(product)
                        }
                    )
                }
            } else {
                item {
                    Text(
                        text = "No favorite products yet.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }
        }
    }*/
}