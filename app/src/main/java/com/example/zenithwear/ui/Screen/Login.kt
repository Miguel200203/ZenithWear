package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R
import com.example.zenithwear.data.Model.UserProfile
import com.example.zenithwear.data.Model.UserPreferences
import com.example.zenithwear.data.Model.network.RetrofitClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Login(navHostController: NavHostController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isUsernameValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Recuperar username del usuario guardado al iniciar la pantalla
    LaunchedEffect(Unit) {
        userPreferences.userFlow.collectLatest { savedUser ->
            savedUser?.let {
                username = TextFieldValue(it.user)
                isUsernameValid = it.user.isNotBlank()
                // Opcional: podrías prellenar password si quieres, aunque no es recomendable
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            imagen()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Enter your username and password to log in", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    isUsernameValid = it.text.isNotBlank()
                    errorMessage = null
                },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                isError = !isUsernameValid && username.text.isNotEmpty(),
                singleLine = true
            )
            if (!isUsernameValid && username.text.isNotEmpty()) {
                Text(
                    "Username cannot be empty",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.text.isNotEmpty()
                    errorMessage = null
                },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                isError = !isPasswordValid && password.text.isNotEmpty(),
                singleLine = true
            )
            if (!isPasswordValid && password.text.isNotEmpty()) {
                Text(
                    "Password cannot be empty",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        item {
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        errorMessage = null
                        try {
                            val response = RetrofitClient.apiService.getUsers()
                            if (response.isSuccessful) {
                                val users = response.body() ?: emptyList<UserProfile>()
                                val user = users.find {
                                    it.user == username.text && it.password == password.text
                                }
                                if (user != null) {
                                    // Guardar usuario completo en DataStore
                                    userPreferences.saveUser(user)
                                    navHostController.navigate("HomePage") {
                                        popUpTo("Login") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Invalid username or password"
                                }
                            } else {
                                errorMessage = "Error contacting server"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Network error: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = isUsernameValid && isPasswordValid && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", fontSize = 18.sp)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Forgot Password?",
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        if (isUsernameValid) {
                            navHostController.navigate("ChangePasswordScreen")
                        }
                    }
                    .padding(8.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun imagen() {
    androidx.compose.foundation.Image(
        modifier = Modifier
            .padding(25.dp)
            .clip(RoundedCornerShape(45.dp)),
        painter = painterResource(id = R.drawable.icono),
        contentDescription = "Logo"
    )
}
