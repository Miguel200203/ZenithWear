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
fun Login (navHostController: NavHostController){
    LazyColumn (
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        item {
            imagen()
            Text("Enter your email to log in to the app", fontSize = 16.sp)
        }
        item{
            CuadroTextoCorreo()
            cuadroPassword()
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(15.dp)
                    .width(310.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = { navHostController.navigate("SignUp") }
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }

}@Composable
fun CuadroTextoCorreo() {

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var estaEscribiendo by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            estaEscribiendo = true
        },
        label = { if (!estaEscribiendo) Text("Correo electrónico") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Blue, // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        ),
        shape = RoundedCornerShape(8.dp), // Bordes redondeados
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        placeholder = { if (!estaEscribiendo) Text("Ingresa tu correo") }
    )
}

@Composable
fun cuadroPassword() {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var estaEscribiendo by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            estaEscribiendo = true
        },
        label = { if (!estaEscribiendo) Text("Contraseña") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        placeholder = { if (!estaEscribiendo) Text("Ingresa tu contraseña") },
        visualTransformation = PasswordVisualTransformation() // Oculta la contraseña
    )
}

@Composable
fun imagen(){

        Image(
            modifier = Modifier.padding(25.dp)
                .clip(RoundedCornerShape(45.dp)),
            painter = painterResource(R.drawable.icono),
            contentDescription = "Logo"

        )
    }
@Composable
fun button(navHostController: NavHostController){

        ExtendedFloatingActionButton(
            modifier = Modifier.padding(15.dp)
                .width(210.dp)
                .background(Color.Black),
            onClick = { navHostController.navigate("SignUp") }
        ) {
            Text(
                "Continue",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }


}