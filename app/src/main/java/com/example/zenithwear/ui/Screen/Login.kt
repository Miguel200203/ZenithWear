package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R

@Composable
fun Login(navHostController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            imagen()
            Text("Enter your email to log in to the app", fontSize = 16.sp)
        }
        item {
            CuadroTextoCorreo(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    isEmailValid = isValidEmail(newValue.text)
                },
                isError = !isEmailValid && email.text.isNotEmpty()
            )
            cuadroPassword(
                value = password,
                onValueChange = { newValue ->
                    password = newValue
                    isPasswordValid = newValue.text.isNotEmpty()
                },
                isError = !isPasswordValid && password.text.isNotEmpty()
            )
        }
        item {
            Button(
                modifier = Modifier.padding(15.dp)
                    .width(310.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = { navHostController.navigate("HomePage") },
                enabled = isEmailValid && isPasswordValid
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CuadroTextoCorreo(
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
            focusedIndicatorColor = if (isError) Color.Red else Color.Blue, // Cambiar color si hay error
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
    if (isError) {
        Text(
            text = "Correo no válido",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun cuadroPassword(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean
) {
    var estaEscribiendo by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { if (!estaEscribiendo) Text("Contraseña") },
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
        placeholder = { if (!estaEscribiendo) Text("Ingresa tu contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        isError = isError
    )
    if (isError) {
        Text(
            text = "La contraseña no puede estar vacía",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun imagen() {
    Image(
        modifier = Modifier.padding(25.dp)
            .clip(RoundedCornerShape(45.dp)),
        painter = painterResource(R.drawable.icono),
        contentDescription = "Logo"
    )
}


fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}