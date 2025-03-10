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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenithwear.ui.theme.ZenithWearTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.zenithwear.ui.Screen.HomeScreen
import com.example.zenithwear.ui.Screen.Login
import com.example.zenithwear.ui.Screen.SignUp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        ComposableMultiScreenApp()
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
    NavHost(navController = navController, startDestination = "Home_Screen"){
        composable("Home_Screen"){ HomeScreen(navController) }
        composable("Login"){ Login(navController)}
        composable("SignUp"){ SignUp(navController) }


    }
}

