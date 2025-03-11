package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R

@Composable
fun SignUp(navHostController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Imagen()
            Text("Enter your email to sign up to the app", fontSize = 16.sp)
        }
        item {
            CuadroTextocorreo(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    isEmailValid = isValidEmail1(newValue.text) // Validar el correo
                    showEmailError = newValue.text.isNotEmpty() && !isEmailValid
                },
                isError = showEmailError
            )
            if (showEmailError) {
                Text(
                    text = "Correo no válido",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(15.dp)
                    .width(310.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    if (isEmailValid) {
                        navHostController.navigate("ConfirmPasswordScreen")
                    } else {
                        showEmailError = true
                    }
                }
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
        }
        item {
            googlebutton()
        }
    }
}

@Composable
fun CuadroTextocorreo(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean
) {
    var estaEscribiendo by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { if (!estaEscribiendo) Text("Correo electrónico") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = if (isError) Color.Red else Color.Blue,
            unfocusedIndicatorColor = if (isError) Color.Red else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        placeholder = { if (!estaEscribiendo) Text("Ingresa tu correo") },
        isError = isError
    )
}

@Composable
fun Imagen() {
    Image(
        modifier = Modifier.padding(25.dp)
            .clip(RoundedCornerShape(45.dp)),
        painter = painterResource(R.drawable.icono),
        contentDescription = "Logo"
    )
}

@Composable
fun googlebutton() {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .padding(15.dp)
            .width(310.dp),
        contentColor = Color.Black,
        containerColor = Color.White,
        onClick = {
            // Acción para registrarse con Google
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(24.dp)
            )
            Text(
                "Sign in with Google",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

// Función para validar el formato del correo electrónico

fun isValidEmail1(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(emailRegex.toRegex())
}