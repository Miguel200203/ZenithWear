package com.example.zenithwear.ui.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenithwear.data.Model.Category
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.network.RetrofitClient
import com.example.zenithwear.data.Model.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(navHostController: NavHostController, cartViewModel: CartViewModel){
    val scope = rememberCoroutineScope()
    val selectedCategory = remember { mutableStateOf<Category?>(null) }
    val selectedProduct = remember { mutableStateOf<Product?>(null) }

    val categoriesState = produceState<List<Category>?>(initialValue = null) {
        value = try{
            val response = RetrofitClient.apiService.getCategories()
            if(response.isSuccessful) response.body() else emptyList()
        } catch(e: Exception){
            emptyList()
        }
    }

    val productsState = produceState<List<Product>?>(initialValue = null) {
        value = try {
            val response = RetrofitClient.apiService.getProducts()
            if (response.isSuccessful) response.body() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    val products = productsState.value
    val categories = categoriesState.value

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                categories == null || products == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                categories.isEmpty() -> {
                    Text("No categories available.", modifier = Modifier.align(Alignment.Center))
                }
                selectedProduct.value != null -> {
                    DescripcionProductoPantallaCompleta(
                        product = selectedProduct.value!!,
                        onBack = { selectedProduct.value = null },
                        cartViewModel = cartViewModel
                    )
                }
                selectedCategory.value == null -> {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        categories.forEach { category ->
                            Text(
                                text = category.name,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedCategory.value = category }
                                    .padding(16.dp)
                            )
                            Divider()
                        }
                    }
                }
                else -> {
                    val filtered = products.filter { it.category == selectedCategory.value?.id }
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            text = "Products in ${selectedCategory.value?.name}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                        filtered.forEach { product ->
                            ProductItem(product = product) {
                                selectedProduct.value = product
                            }
                        }
                        Button(
                            onClick = { selectedCategory.value = null },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        ) {
                            Text("Back to categories")
                        }
                    }
                }
            }
        }
    }
}