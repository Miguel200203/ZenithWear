import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.ClimaData
import com.example.zenithwear.data.Model.OcasionesData
import com.example.zenithwear.ui.Component.CartViewModel
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.ui.Screen.Bars
import com.example.zenithwear.ui.Screen.Bars2

@Composable
fun IA(navHostController: NavHostController, cartViewModel: CartViewModel) {
    var productosRecomendados by remember { mutableStateOf<List<ModelProduct>>(emptyList()) }

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }
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
                    text = "Personalized recommendations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(15.dp)
                )
            }

            item {
                Column(modifier = Modifier.padding(10.dp)) {
                    Button(
                        onClick = { mostrarRecomendaciones("clima", "Soleado") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Clima (Soleado)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("clima", "Lluvia") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Clima (Lluvia)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("clima", "Frío") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Clima (Frío)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("ocasión", "Trabajo") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Ocasión (Trabajo)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("ocasión", "Cita") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Ocasión (Cita)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("ocasión", "Deporte") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Ocasión (Deporte)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("ocasión", "Viaje") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Ocasión (Viaje)")
                    }

                    Button(
                        onClick = { mostrarRecomendaciones("ocasión", "Fiesta") { productosRecomendados = it } },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Recomendaciones por Ocasión (Fiesta)")
                    }
                }
            }

            items(productosRecomendados) { producto ->
                Text(
                    text = producto.title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

fun mostrarRecomendaciones(tipo: String, criterio: String, updateRecomendados: (List<ModelProduct>) -> Unit) {
    val productos = when (tipo) {
        "clima" -> ClimaData.climaRecomendaciones[criterio]?.flatten() ?: emptyList()
        "ocasión" -> OcasionesData.ocasionesRecomendaciones[criterio]?.flatten() ?: emptyList()
        else -> emptyList()
    }

    updateRecomendados(productos)
}
