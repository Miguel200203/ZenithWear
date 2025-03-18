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
import com.example.zenithwear.ui.theme.ZenithWearTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.zenithwear.ui.Screen.Cart
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
    SetupNavGraph(navController = navController)
}
@Composable
fun SetupNavGraph(navController : NavHostController){
    NavHost(navController = navController, startDestination = "HomePage"){
        composable("Home_Screen"){ HomeScreen(navController) }
        composable("Login"){ Login(navController)}
        composable("SignUp"){ SignUp(navController) }
        composable("HomePage"){ HomePage(navController) }
        composable("ConfirmPasswordScreen"){ ConfirmPasswordScreen(navController)}
        composable("PersonalInformation"){ PersonalInformation(navController)}
        composable("Cart"){ Cart(navController) }
        composable("Favorite"){ Favorite(navController)}
        composable("IA"){ IA(navController)}
        composable("Notification"){ Notification(navController)}
        composable("Products"){ Products(navController)}
        composable("Profile"){ Profile(navController)}
        composable("Search"){ Search(navController)}
    }
}

