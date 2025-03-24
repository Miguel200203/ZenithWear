package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(navHostController: NavHostController) {
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var showError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Crear un ámbito de corrutina

    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Change Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            cuadroPassword1(
                value = newPassword,
                onValueChange = { newValue ->
                    newPassword = newValue
                    showError = false
                },
                isError = showError && newPassword.text != confirmPassword.text
            )
        }
        item {
            cuadroPassword1(
                value = confirmPassword,
                onValueChange = { newValue ->
                    confirmPassword = newValue
                    showError = false
                },
                isError = showError && newPassword.text != confirmPassword.text
            )
        }
        item {
            if (showError && newPassword.text != confirmPassword.text) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        item {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
                Text(
                    text = "Changing password...",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                Button(
                    modifier = Modifier.padding(16.dp)
                        .width(310.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (newPassword.text == confirmPassword.text) {
                            isLoading = true

                            scope.launch {
                                delay(3000)
                                navHostController.navigate("Login")
                            }
                        } else {
                            showError = true
                        }
                    }
                ) {
                    Text(
                        text = "Change Password",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
@Composable
fun cuadroPassword1(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = if (isError) Color.Red else Color.Blue, // Cambiar color si hay error
            unfocusedIndicatorColor = if (isError) Color.Red else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        isError = isError
    )
}
