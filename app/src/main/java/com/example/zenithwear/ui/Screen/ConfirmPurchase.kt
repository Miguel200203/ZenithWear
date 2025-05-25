package com.example.zenithwear.ui.Screen

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPurchase(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val context = LocalContext.current
    val activity = context.findFragmentActivity()

    val cartProducts by cartViewModel.cartProducts.collectAsState(initial = emptyList())
    val totalPrice = cartProducts.sumOf { it.price?.toDouble() ?: 0.0 }

    var paymentMethod by remember { mutableStateOf("Cash") }
    var cardNumber by remember { mutableStateOf("") }
    val isCardValid = cardNumber.length == 16

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm Purchase") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Cart")
                    }
                }
            )
        },
        bottomBar = {
            Bars2(navHostController, cartViewModel)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Total: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)

            Text("Select Payment Method:", style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { paymentMethod = "Cash" }
                ) {
                    RadioButton(
                        selected = paymentMethod == "Cash",
                        onClick = { paymentMethod = "Cash" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Cash")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { paymentMethod = "Card" }
                ) {
                    RadioButton(
                        selected = paymentMethod == "Card",
                        onClick = { paymentMethod = "Card" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Card")
                }
            }

            if (paymentMethod == "Card") {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16 && it.all { c -> c.isDigit() }) {
                            cardNumber = it
                        }
                    },
                    label = { Text("Card Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cardNumber.isNotEmpty() && !isCardValid,
                    modifier = Modifier.fillMaxWidth()
                )
                if (cardNumber.isNotEmpty() && !isCardValid) {
                    Text("Card must have 16 digits", color = MaterialTheme.colorScheme.error)
                }
            }

            Button(
                onClick = {
                    if (paymentMethod == "Card" && !isCardValid) {
                        Toast.makeText(context, "Please enter a valid 16-digit card number.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (activity != null) {
                        showBiometricPrompt(
                            activity = activity,
                            onSuccess = {
                                Toast.makeText(context, "Purchase Confirmed!", Toast.LENGTH_LONG).show()
                                // Aquí puedes limpiar el carrito o navegar a otra pantalla
                            },
                            onError = { error ->
                                Toast.makeText(context, "Authentication failed: $error", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "No se pudo obtener la actividad para autenticación", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = paymentMethod == "Cash" || isCardValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm")
            }
        }
    }
}

// Función para mostrar el prompt biométrico con androidx.biometric
fun showBiometricPrompt(
    activity: FragmentActivity,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)
    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errString.toString())
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate Purchase")
        .setSubtitle("Use your fingerprint or device PIN")
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}

// Helper para obtener FragmentActivity desde un Context
fun Context.findFragmentActivity(): FragmentActivity? {
    var ctx = this
    while (ctx is android.content.ContextWrapper) {
        if (ctx is FragmentActivity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
