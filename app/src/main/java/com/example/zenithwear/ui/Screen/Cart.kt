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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.ui.Component.CartViewModel


@Composable
fun Cart(navHostController: NavHostController, cartViewModel: CartViewModel) {
    /*val showPaymentDialog = remember { mutableStateOf(false) } // Diálogo para el método de pago
    val showConfirmationDialog = remember { mutableStateOf(false) } // Diálogo de confirmación
    val showSuccessDialog = remember { mutableStateOf(false) } // Diálogo de éxito
    val paymentMethod = remember { mutableStateOf("Cash") } // Método de pago seleccionado
    val cardNumber = remember { mutableStateOf("") } // Número de tarjeta

    // Función para validar el número de tarjeta
    fun isValidCardNumber(cardNumber: String): Boolean {
        return cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    }

    Scaffold(
        topBar = { Bars(navHostController) }, // Barra superior
        bottomBar = { Bars2(navHostController, cartViewModel) } // Barra inferior
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Lista de productos en el carrito
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                if (cartViewModel.cartProducts.isNotEmpty()) {
                    items(cartViewModel.cartProducts) { product ->
                        ProductCard(
                            product = product,
                            onRemove = { cartViewModel.removeProductFromCart(product) }
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "Empty cart. Find your favorite products!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }

            // Mostrar la suma total
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "$${cartViewModel.cartProducts.sumBy { it.precio }}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón para abrir el diálogo de pago
            Button(
                onClick = { showPaymentDialog.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Select Payment Method")
            }
        }
    }

    // Diálogo para seleccionar el método de pago y completar la compra
    if (showPaymentDialog.value) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog.value = false },
            title = { Text(text = "Payment Details") },
            text = {
                Column {
                    // Selección del método de pago usando RadioButton
                    Text(text = "Select Payment Method:", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Opción para "Cash"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { paymentMethod.value = "Cash" }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = paymentMethod.value == "Cash",
                            onClick = { paymentMethod.value = "Cash" }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Cash", fontSize = 16.sp)
                    }

                    // Opción para "Card"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { paymentMethod.value = "Card" }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = paymentMethod.value == "Card",
                            onClick = { paymentMethod.value = "Card" }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Card", fontSize = 16.sp)
                    }

                    // Si el método de pago es "Card", mostrar campo para el número de tarjeta
                    if (paymentMethod.value == "Card") {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = cardNumber.value,
                            onValueChange = { cardNumber.value = it },
                            label = { Text("Card Number") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Botón para completar la compra
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (paymentMethod.value == "Card" && !isValidCardNumber(cardNumber.value)) {
                                // Mostrar error si el número de tarjeta no es válido
                                return@Button
                            }
                            showPaymentDialog.value = false
                            showConfirmationDialog.value = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = paymentMethod.value == "Cash" || isValidCardNumber(cardNumber.value)
                    ) {
                        Text(text = "Complete Purchase")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPaymentDialog.value = false }
                ) {
                    Text(text = "Close")
                }
            }
        )
    }

    // Diálogo de confirmación de compra
    if (showConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog.value = false },
            title = { Text(text = "Confirm Purchase") },
            text = { Text(text = "Are you sure you want to complete the purchase?") },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog.value = false
                        showSuccessDialog.value = true
                        cartViewModel.clearCart() // Limpiar el carrito después de la compra
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmationDialog.value = false }
                ) {
                    Text(text = "No")
                }
            }
        )
    }

    // Diálogo de compra exitosa
    if (showSuccessDialog.value) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog.value = false },
            title = { Text(text = "Purchase Successful") },
            text = { Text(text = "Your purchase has been completed successfully!") },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog.value = false }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Composable
fun ProductCard(product: ModelProduct, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
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
                Text(text = "Price: $${product.precio}", fontSize = 14.sp)
            }
            IconButton(
                onClick = onRemove // Elimina el producto del carrito
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from cart"
                )
            }
        }
    }*/
}
