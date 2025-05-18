package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.navigation.NavHostController
import com.example.zenithwear.R
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.ui.Component.CartViewModel
import com.example.zenithwear.data.Model.ProductsData
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.network.RetrofitClient

@Composable
fun Products(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val scope = rememberCoroutineScope()

    val productsState = produceState<List<Product>?>(initialValue = null){
        value = try{
            val response = RetrofitClient.apiService.getProducts()
            if(response.isSuccessful) response.body() else emptyList()
        } catch(e: Exception){
            emptyList()
        }
    }

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (selectedProduct.value == null) {
                val products = productsState.value
                when {
                    products == null -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    products.isEmpty() -> {
                        Text("No products available.", modifier = Modifier.align(Alignment.Center))
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Text(
                                text = "Discover what we have for you.",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(15.dp)
                            )
                            products.forEach { product ->
                                ProductItem(product = product) {
                                    selectedProduct.value = product
                                }
                            }
                        }
                    }
                }
            } else {
                DescripcionProductoPantallaCompleta(
                    product = selectedProduct.value!!,
                    onBack = { selectedProduct.value = null },
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = getDrawableResource(product.image)),
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ){
                Text(product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Category: ${product.category}", fontSize = 14.sp)
                Text("Price: $${product.price}", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun DescripcionProductoPantallaCompleta(
    product: Product,
    onBack: () -> Unit,
    cartViewModel: CartViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Image(
            painter = painterResource(id = getDrawableResource(product.image)),
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(product.title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text("Price: $${product.price}", fontSize = 18.sp)
        Text("Category: ${product.category}", fontSize = 16.sp)
        Text("Brand: ${product.brand ?: "Unknown"}", fontSize = 16.sp)
        Text("Size: ${product.size}", fontSize = 16.sp)
        Text("Available: ${product.available}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Text("Description: ${product.description}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(onClick = {
                if(cartViewModel.isProductInFavorites(product)){
                    cartViewModel.removeProductFromFavorites(product)
                } else{
                    cartViewModel.addProductToFavorites(product)
                }
            }) {
                Icon(
                    imageVector = if(cartViewModel.isProductInFavorites(product))
                    Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if(cartViewModel.isProductInFavorites(product)) Color.Red else Color.Gray
                )
            }

            IconButton(onClick = {
                cartViewModel.addProductToCart(product)
            }) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Cart"
                )
            }
        }
    }
}

fun getDrawableResource(imageId: Int): Int{
    return when(imageId){
        1 -> R.drawable.sudadera
        2 -> R.drawable.ropah
        3 -> R.drawable.ropam
        4 -> R.drawable.ropak
        5 -> R.drawable.collections
        6 -> R.drawable.curry
        7 -> R.drawable.footwear
        8 -> R.drawable.samba
        9 -> R.drawable.accessories
        10 -> R.drawable.mochila
        11 -> R.drawable.sportwear
        else -> R.drawable.error
    }
}