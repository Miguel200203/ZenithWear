package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zenithwear.data.Model.Brand
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.network.RetrofitClient
import com.example.zenithwear.ui.Component.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Brands(navController: NavController, cartViewModel: CartViewModel){
    val scope = rememberCoroutineScope()
    val selectBrand = remember { mutableStateOf<Brand?>(null) }
    val selectedProduct = remember { mutableStateOf<Product?>(null) }

    val brandsState = produceState<List<Brand>?>(initialValue = null){
        value = try{
            val response = RetrofitClient.apiService.getBrands()
            if(response.isSuccessful) response.body() else emptyList()
        } catch(e: Exception){
            emptyList()
        }
    }

    val productsState = produceState<List<Product>?>(initialValue = null){
        value = try{
            val response = RetrofitClient.apiService.getProducts()
            if(response.isSuccessful) response.body() else emptyList()
        } catch(e: Exception){
            emptyList()
        }
    }

    val products = productsState.value
    val brands = brandsState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Brands") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ){
            when{
                brands == null || products == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                brands.isEmpty() -> {
                    Text("No brands available.", modifier = Modifier.align(Alignment.Center))
                }
                selectedProduct.value != null -> {
                    DescripcionProductoPantallaCompleta(
                        product = selectedProduct.value!!,
                        onBack = { selectedProduct.value = null },
                        cartViewModel = cartViewModel
                    )
                }
                selectBrand.value == null -> {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ){
                        brands.forEach{ brand ->
                            Text(
                                text = brand.name,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable{ selectBrand.value = brand }
                                    .padding(16.dp)
                            )
                            Divider()
                        }
                    }
                } else -> {
                val filtered = products.filter { it.brand == selectBrand.value?.id }
                Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Products by ${selectBrand.value?.name}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                        filtered.forEach() { product ->
                            ProductItem(product = product) {
                                selectedProduct.value = product
                            }
                        }
                        Button(
                            onClick = {selectBrand.value = null},
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        ){
                            Text("Back to brands")
                        }
                    }
                }
            }
        }
    }
}