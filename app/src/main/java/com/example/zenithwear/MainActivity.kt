package com.example.zenithwear

import IA
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zenithwear.ui.theme.ZenithWearTheme
import com.example.zenithwear.ui.Component.CartViewModel
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Si estás utilizando edge-to-edge
        setContent {
            ZenithWearTheme {
                ComposableMultiScreenApp()
            }
        }
    }
}

@Composable
fun ComposableMultiScreenApp(){
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel() // Crea el ViewModel
    SetupNavGraph(navController = navController, cartViewModel = cartViewModel)
}

@Composable
fun SetupNavGraph(navController: NavHostController, cartViewModel: CartViewModel){
    NavHost(navController = navController, startDestination = "HomePage"){
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
        composable("Brands") { Brands(navController, cartViewModel) }
        composable("Categories") { Categories(navController, cartViewModel) }
        composable("Profile") { Profile(navController, cartViewModel) }
        composable("Search") { Search(navController, cartViewModel) }
        composable("VerificationCodeScreen") { VerificationCodeScreen(navController) }
        composable("ChangePasswordScreen") { ChangePasswordScreen(navController) }
        composable("offers_screen") { HomePage(navController, cartViewModel) }
        composable("flash_sale") { Products(navController, cartViewModel) }
        composable("new_arrivals") { Brands(navController, cartViewModel) }
        composable("vip_event") { Categories(navController, cartViewModel) }
    }
}
