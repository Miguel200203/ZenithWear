package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel

@Composable
fun Notification(navHostController: NavHostController,cartViewModel: CartViewModel){
    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController,cartViewModel) }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = "Check out these amazing discounts and offers on clothing!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(15.dp)
                )
            }

            item {
                NotificationDiscount(
                    title = "50% OFF on Summer Collection!",
                    message = "Hurry up! Don't miss the 50% off on our summer collection. Limited time offer.",
                    onClick = { navHostController.navigate("offers_screen") }
                )
            }

            item {
                NotificationSaleAlert(
                    title = "Flash Sale! Up to 70% OFF",
                    message = "Our flash sale starts now. Shop your favorite clothing at unbeatable prices!",
                    onClick = { navHostController.navigate("flash_sale") }
                )
            }

            item {
                NotificationNewArrival(
                    title = "New Arrivals! Fresh Styles",
                    message = "Check out our latest arrivals. Fresh styles added to the collection just for you!",
                    onClick = { navHostController.navigate("new_arrivals") }
                )
            }

            item {
                NotificationEvent(
                    title = "Exclusive VIP Sale Event",
                    message = "You are invited to our exclusive VIP sale event with additional discounts on select items!",
                    onClick = { navHostController.navigate("vip_event") }
                )
            }
        }
    }
}

@Composable
fun NotificationDiscount(title: String, message: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = message, fontSize = 14.sp)
            Button(
                onClick = onClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Get Offer")
            }
        }
    }
}

@Composable
fun NotificationSaleAlert(title: String, message: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = message, fontSize = 14.sp)
            Button(
                onClick = onClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Shop Now")
            }
        }
    }
}

@Composable
fun NotificationNewArrival(title: String, message: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB2DFDB))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = message, fontSize = 14.sp)
            Button(
                onClick = onClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Browse New Arrivals")
            }
        }
    }
}

@Composable
fun NotificationEvent(title: String, message: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Cambié de fillMaxSize a fillMaxWidth
            .padding(8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE082))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = message, fontSize = 14.sp)
            Button(
                onClick = onClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("RSVP Now")
            }
        }
    }
}