package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.data.Model.ProductsData
import com.example.zenithwear.ui.Component.CartViewModel

@Composable
fun Products(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val selectedProduct = remember { mutableStateOf<ModelProduct?>(null) }

    Scaffold(
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
            item {
                Text(
                    text = "Discover what we have for you.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(15.dp)
                )
            }
            items(ProductsData.allProducts, key = { product -> product.id }) { product ->
                ProductItem(
                    product = product,
                    onClick = {
                        println("Producto seleccionado: ${product.title}")
                        selectedProduct.value = product
                    }
                )
            }
        }
        selectedProduct.value?.let { product ->
            DescripcionProducto(
                product = product,
                onDismiss = { selectedProduct.value = null },
                navHostController = navHostController,
                cartViewModel = cartViewModel
            )
        }
    }
}

@Composable
fun ProductItem(
    product: ModelProduct,
    onClick: () -> Unit,
    onRemove: (() -> Unit)? = null // Parámetro opcional para eliminar
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Categoría: ${product.categoria}", fontSize = 14.sp)
                Text(text = "Precio: $${product.precio}", fontSize = 14.sp)
            }
            // Mostrar el botón de eliminar solo si onRemove no es nulo
            if (onRemove != null) {
                IconButton(
                    onClick = onRemove
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar de favoritos"
                    )
                }
            }
        }
    }
}
@Composable
fun DescripcionProducto(
    product: ModelProduct,
    onDismiss: () -> Unit,
    navHostController: NavHostController,
    cartViewModel: CartViewModel
) {
    // Verifica si el producto está en favoritos
    val isFavorite = cartViewModel.isProductInFavorites(product)

    // Estado para controlar el ícono de favoritos
    var favoriteIcon by remember { mutableStateOf(if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder) }
    var favoriteIconColor by remember { mutableStateOf(if (isFavorite) Color.Red else Color.Gray) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = product.title, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Price: $${product.precio}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Category: ${product.categoria}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Brand: ${product.marca}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Description: ${product.descripcion}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Size: ${product.talla}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Available: ${product.disponibles}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            // Agrega o elimina el producto de favoritos
                            if (isFavorite) {
                                cartViewModel.removeProductFromFavorites(product)
                                favoriteIcon = Icons.Default.FavoriteBorder
                                favoriteIconColor = Color.Gray
                            } else {
                                cartViewModel.addProductToFavorites(product)
                                favoriteIcon = Icons.Filled.Favorite
                                favoriteIconColor = Color.Red
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = favoriteIcon,
                            contentDescription = "Add to favorites",
                            tint = favoriteIconColor
                        )
                    }

                    IconButton(
                        onClick = {
                            // Agrega el producto al carrito
                            cartViewModel.addProductToCart(product)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to cart"
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}