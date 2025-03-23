package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
@Composable
fun VerificationCodeScreen(navHostController: NavHostController) {
    var code by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(true) }

    // Mostrar el AlertDialog si showDialog es true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                navHostController.popBackStack() // Cerrar el diálogo y regresar a la pantalla anterior
            },
            title = { Text("Enter Verification Code") },
            text = {
                Column {
                    Text("Please enter the 4-digit code sent to your email.")
                    OutlinedTextField(
                        value = code,
                        onValueChange = { code = it },
                        label = { Text("Code") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number, // Asegurar que solo se ingresen números
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 1,
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (code.length == 4) {
                            showDialog = false
                            navHostController.navigate("ChangePasswordScreen") // Navegar a la pantalla de cambio de contraseña
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                        navHostController.popBackStack() // Cerrar el diálogo y regresar a la pantalla anterior
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}