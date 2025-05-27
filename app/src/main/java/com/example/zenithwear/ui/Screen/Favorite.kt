package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.ui.Component.CartViewModel
import com.example.zenithwear.ui.Screen.getDrawableResource

@Composable
fun Favorite(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val favoriteProducts = cartViewModel.favoriteProducts
    var productDetailToShow by remember { mutableStateOf<Product?>(null) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { Bars(navController) },
        bottomBar = { Bars2(navController, cartViewModel) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (favoriteProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "There is no product selected as a favorite",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(favoriteProducts) { product ->
                        ProductItemFavorite(
                            product = product,
                            onRemoveFavorite = {
                                cartViewModel.removeProductFromFavorites(product)
                            },
                            onProductClick = {
                                productDetailToShow = it
                            }
                        )
                        Divider()
                    }
                }
            }

            if (productDetailToShow != null) {
                AlertDialog(
                    onDismissRequest = { productDetailToShow = null },
                    title = {
                        Text(text = productDetailToShow!!.title, style = MaterialTheme.typography.titleLarge)
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)
                        ) {
                            Image(
                                painter = painterResource(id = getDrawableResource(productDetailToShow!!.image)),
                                contentDescription = productDetailToShow!!.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(bottom = 12.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Category: ${productDetailToShow!!.category}")
                            Text("Price: $${productDetailToShow!!.price}")
                            Text("Brand: ${productDetailToShow!!.brand ?: "Unknown"}")
                            Text("Size: ${productDetailToShow!!.size}")
                            Text("Available: ${productDetailToShow!!.available}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Description: ${productDetailToShow!!.description}")
                        }
                    },
                    confirmButton = {
                        Button(onClick = { productDetailToShow = null }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItemFavorite(
    product: Product,
    onRemoveFavorite: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onProductClick(product) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = getDrawableResource(product.image)),
            contentDescription = product.title,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = product.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = onRemoveFavorite) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove from favorites",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
