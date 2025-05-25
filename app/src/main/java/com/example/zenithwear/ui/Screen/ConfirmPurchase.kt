package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPurchase(navHostController: NavHostController, cartViewModel: CartViewModel) {
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
        // Aquí puedes poner el contenido principal de ConfirmPurchase
        Text(
            text = "This is the Confirm Purchase screen",
            modifier = Modifier.padding(paddingValues)
        )
    }
}