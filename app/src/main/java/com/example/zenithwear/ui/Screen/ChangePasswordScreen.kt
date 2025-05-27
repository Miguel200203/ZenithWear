package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.UserProfile
import com.example.zenithwear.data.Model.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(navHostController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var userFound by remember { mutableStateOf<UserProfile?>(null) }
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text("Change Password", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        }

        item {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    errorMessage = null
                },
                label = { Text("Enter your username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            val response = RetrofitClient.apiService.getUsers()
                            if (response.isSuccessful) {
                                val users = response.body() ?: emptyList()
                                val user = users.find { it.user == username.text }
                                if (user != null) {
                                    userFound = user
                                } else {
                                    errorMessage = "User not found"
                                    userFound = null
                                }
                            } else {
                                errorMessage = "Failed to connect to server"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Confirm", fontSize = 18.sp)
                }
            }
        }

        if (userFound != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        errorMessage = null
                    },
                    label = { Text("New Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = null
                    },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            if (newPassword.text != confirmPassword.text) {
                                errorMessage = "Passwords do not match"
                                return@launch
                            }
                            if (newPassword.text.isBlank()) {
                                errorMessage = "Password cannot be empty"
                                return@launch
                            }

                            try {
                                val updatedUser = userFound!!.copy(password = newPassword.text)
                                val response = RetrofitClient.apiService.updateUser(userFound!!.id!!, updatedUser)
                                if (response.isSuccessful) {
                                    // Navega a Login sin leer body para evitar error
                                    navHostController.navigate("Login") {
                                        popUpTo("ChangePasswordScreen") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Failed to update password"
                                }
                            } catch (e: Exception) {
                                errorMessage = " "
                                navHostController.navigate("Login") {
                                    popUpTo("ChangePasswordScreen") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Update Password", fontSize = 18.sp)
                }
            }
        }

        item {
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
