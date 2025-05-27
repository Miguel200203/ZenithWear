package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.ui.Component.CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(navController: NavHostController, cartViewModel: CartViewModel) {
    // Guardamos cantidades por producto id
    val quantities = remember { mutableStateMapOf<Int, Int>() }
    val cartProducts = cartViewModel.cartProducts.collectAsState(initial = emptyList())

    // Inicializamos cantidades en 1 para cada producto
    cartProducts.value.forEach { product ->
        if (quantities[product.id] == null) {
            quantities[product.id] = 1
        }
    }

    // Calculamos total tomando en cuenta cantidades
    val totalPrice = cartProducts.value.sumOf { product ->
        val qty = quantities[product.id] ?: 1
        (product.price?.toDouble() ?: 0.0) * qty
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Car") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = { Bars2(navController, cartViewModel) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (cartProducts.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("El carrito está vacío.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartProducts.value) { product ->
                        CartItemCard(
                            product = product,
                            quantity = quantities[product.id] ?: 1,
                            onQuantityChange = { newQty ->
                                quantities[product.id] = newQty
                            },
                            onRemoveFromCart = {
                                cartViewModel.removeProductFromCart(product)
                                quantities.remove(product.id)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total Price: $${"%.2f".format(totalPrice)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("confirm") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Confirm Purchase")
            }
        }
    }
}

@Composable
fun CartItemCard(
    product: Product,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onRemoveFromCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(140.dp) // un poco más alto para los controles
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .background(Color.White)
                    .size(100.dp),
                painter = painterResource(id = getDrawableResource(product.image)),
                contentDescription = product.title,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: $${product.price}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Quantity:", modifier = Modifier.padding(end = 8.dp))

                    IconButton(
                        onClick = {
                            if (quantity > 1) onQuantityChange(quantity - 1)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("-", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier.width(32.dp),
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = {
                            onQuantityChange(quantity + 1)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                val totalItemPrice = (product.price?.toDouble() ?: 0.0) * quantity
                Text(
                    text = "Total: $${"%.2f".format(totalItemPrice)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onRemoveFromCart) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar del carrito",
                    tint = Color.Black
                )
            }
        }
    }
}
