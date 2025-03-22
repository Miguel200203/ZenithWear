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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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

@Composable
fun Products(navHostController: NavHostController){
    val selectedProduct = remember { mutableStateOf<ModelProduct?>(null) }
    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController) }
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
                ProductItem(product) {
                    println("Producto seleccionado: ${product.title}")
                    selectedProduct.value = product
                }
            }
        }
        selectedProduct.value?.let { product ->
            println("Mostrando detalles del producto: ${product.title}") // Verificar si el producto seleccionado es pasado al Dialog
            DescripcionProducto(product = product, onDismiss = { selectedProduct.value = null })
        }
    }
}

@Composable
fun ProductItem(product: ModelProduct, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable (onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(10.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column{
                Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Categoria ${product.categoria}", fontSize = 14.sp)
                Text(text = "Precio ${product.precio}", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun DescripcionProducto(product: ModelProduct, onDismiss: () -> Unit) {
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
                    text = "Precio: $${product.precio}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Categoría: ${product.categoria}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Marca: ${product.marca}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Descripción: ${product.descripcion}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Talla: ${product.talla}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Disponibles: ${product.disponibles}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Agregar a favoritos",
                        modifier = Modifier.size(24.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Agregar al carrito",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}