package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R

@Composable
fun HomeScreen(navHostController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color.LightGray)
                )
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        item {
            Image(
                modifier = Modifier
                    .padding(25.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp)),
                painter = painterResource(R.drawable.icono),
                contentDescription = "Logo"
            )
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(15.dp)
                    .width(210.dp),
                onClick = { navHostController.navigate("Login") },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Text(
                    "Log in",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(15.dp)
                    .width(210.dp),
                onClick = { navHostController.navigate("SignUp") },
                containerColor = Color(0xFF00695C),
                        contentColor = Color.White
            ) {
                Text(
                    "Sign up",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}