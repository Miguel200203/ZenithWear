package com.example.zenithwear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zenithwear.ui.theme.ZenithWearTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.zenithwear.ui.Component.CartViewModel
import com.example.zenithwear.ui.Screen.Cart
import com.example.zenithwear.ui.Screen.ChangePasswordScreen
import com.example.zenithwear.ui.Screen.ConfirmPasswordScreen
import com.example.zenithwear.ui.Screen.Favorite
import com.example.zenithwear.ui.Screen.HomePage
import com.example.zenithwear.ui.Screen.HomeScreen
import com.example.zenithwear.ui.Screen.IA
import com.example.zenithwear.ui.Screen.Login
import com.example.zenithwear.ui.Screen.Notification
import com.example.zenithwear.ui.Screen.SignUp
import com.example.zenithwear.ui.Screen.PersonalInformation
import com.example.zenithwear.ui.Screen.Products
import com.example.zenithwear.ui.Screen.Profile
import com.example.zenithwear.ui.Screen.Search
import com.example.zenithwear.ui.Screen.VerificationCodeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
fun SetupNavGraph(navController : NavHostController, cartViewModel: CartViewModel){
    NavHost(navController = navController, startDestination = "Home_Screen"){
        composable("Home_Screen"){ HomeScreen(navController) }
        composable("Login"){ Login(navController)}
        composable("SignUp"){ SignUp(navController) }
        composable("HomePage"){ HomePage(navController, cartViewModel) }
        composable("ConfirmPasswordScreen"){ ConfirmPasswordScreen(navController, cartViewModel)}
        composable("PersonalInformation"){ PersonalInformation(navController)}
        composable("Cart"){ Cart(navController, cartViewModel) }
        composable("Favorite"){ Favorite(navController, cartViewModel)}
        composable("IA"){ IA(navController, cartViewModel)}
        composable("Notification"){ Notification(navController, cartViewModel)}
        composable("Products"){ Products(navController, cartViewModel)}
        composable("Profile"){ Profile(navController, cartViewModel)}
        composable("Search"){ Search(navController, cartViewModel)}
        composable("VerificationCodeScreen"){ VerificationCodeScreen(navController)}
        composable("ChangePasswordScreen"){ ChangePasswordScreen(navController)}
    }
}

