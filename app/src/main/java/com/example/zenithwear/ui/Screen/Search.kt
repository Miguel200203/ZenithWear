package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.network.RetrofitClient
import com.example.zenithwear.ui.Component.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val searchQuery = remember { mutableStateOf("") }
    val selectedProduct = remember { mutableStateOf<Product?>(null) }

    val productsState = produceState<List<Product>?>(initialValue = null) {
        value = try {
            val response = RetrofitClient.apiService.getProducts()
            if (response.isSuccessful) response.body() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    val products = productsState.value ?: emptyList()

    val filteredProducts = remember(searchQuery.value, products) {
        if (searchQuery.value.isBlank()) {
            emptyList()
        } else {
            products.filter {
                it.title.contains(searchQuery.value, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedProduct.value != null) {
                    DescripcionProductoPantallaCompleta(
                        product = selectedProduct.value!!,
                        onBack = { selectedProduct.value = null },
                        cartViewModel = cartViewModel
                    )
                } else {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (filteredProducts.isEmpty()) {
                            Text(
                                "No results found.",
                                fontSize = 16.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(8.dp)
                            )
                        } else {
                            filteredProducts.forEach { product ->
                                ProductItem(product = product) {
                                    selectedProduct.value = product
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}