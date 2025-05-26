package com.example.zenithwear

import IA
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zenithwear.data.Model.NotificationHelper
import com.example.zenithwear.data.Model.ProductsData.allProducts
import com.example.zenithwear.ui.Component.CartViewModel
import com.example.zenithwear.ui.theme.ZenithWearTheme
import com.example.zenithwear.ui.Screen.Brands
import com.example.zenithwear.ui.Screen.Cart
import com.example.zenithwear.ui.Screen.Categories
import com.example.zenithwear.ui.Screen.HomeScreen
import com.example.zenithwear.ui.Screen.Login
import com.example.zenithwear.ui.Screen.SignUp
import com.example.zenithwear.ui.Screen.HomePage
import com.example.zenithwear.ui.Screen.Profile
import com.example.zenithwear.ui.Screen.Search
import com.example.zenithwear.ui.Screen.Notification
import com.example.zenithwear.ui.Screen.Favorite
import com.example.zenithwear.ui.Screen.Products
import com.example.zenithwear.ui.Screen.ChangePasswordScreen
import com.example.zenithwear.ui.Screen.VerificationCodeScreen
import com.example.zenithwear.ui.Screen.PersonalInformation
import com.example.zenithwear.ui.Screen.ConfirmPasswordScreen
import com.example.zenithwear.ui.Screen.ConfirmPurchase
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("NotificationPermission", "Permiso de notificaciones concedido")
                NotificationHelper.createChannel(this)
            } else {
                Log.d("NotificationPermission", "Permiso de notificaciones denegado")
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationHelper.createChannel(this)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            NotificationHelper.createChannel(this)
        }
        enableEdgeToEdge()  // Si estás utilizando edge-to-edge
        val destination = intent?.getStringExtra("destination")
        setContent {
            ZenithWearTheme {
                ComposableMultiScreenApp(destination)
            }
        }
    }
}

@Composable
fun ComposableMultiScreenApp(destination: String?) {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }
    SetupNavGraph(navController = navController, cartViewModel = cartViewModel)
}

@Composable
fun SetupNavGraph(navController: NavHostController, cartViewModel: CartViewModel){
    NavHost(navController = navController, startDestination = "Login"){
        composable("Home_Screen") { HomeScreen(navController) }
        composable("Login") { Login(navController) }
        composable("SignUp") { SignUp(navController) }
        composable("HomePage") { HomePage(navController, cartViewModel) }
        composable("ConfirmPasswordScreen") { ConfirmPasswordScreen(navController, cartViewModel) }
        composable("PersonalInformation") { PersonalInformation(navController) }
        composable("Cart") { Cart(navController, cartViewModel) }
        composable("Favorite") { Favorite(navController, cartViewModel) }
        composable("IA") { IA(navController, cartViewModel) }
        composable("Notification") { Notification(navController, cartViewModel) }
        composable("Products") { Products(navController, cartViewModel) }
        composable("Profile") { Profile(navController, cartViewModel) }
        composable("Search") { Search(navController, cartViewModel) }
        composable("VerificationCodeScreen") { VerificationCodeScreen(navController) }
        composable("ChangePasswordScreen") { ChangePasswordScreen(navController) }
        composable("offers_screen") { HomePage(navController, cartViewModel) }
        composable("flash_sale") { Products(navController, cartViewModel) }
        composable("new_arrivals") { Brands(navController, cartViewModel) }
        composable("vip_event") { Categories(navController, cartViewModel) }
        composable ("confirm"){ ConfirmPurchase(navController, cartViewModel) }
        }
}