package com.example.zenithwear.ui.Screen

import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPurchase(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    var confirmarCompraPendiente by remember { mutableStateOf(false) }
    val cartProducts by cartViewModel.cartProducts.collectAsState(initial = emptyList())
    val totalPrice = cartProducts.sumOf { it.price?.toDouble() ?: 0.0 }

    var paymentMethod by remember { mutableStateOf("Cash") }
    var cardNumber by remember { mutableStateOf("") }
    val isCardValid = cardNumber.length == 16

    var fechaEntregaTexto by remember { mutableStateOf("") }
    var fechaEntregaMillis by remember { mutableStateOf<Long?>(null) }

    val launcherPermisoCalendario = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val concedido = permisos[Manifest.permission.READ_CALENDAR] == true &&
                permisos[Manifest.permission.WRITE_CALENDAR] == true

        if (concedido && confirmarCompraPendiente && fechaEntregaMillis != null) {
            val fin = fechaEntregaMillis!! + 60 * 60 * 1000
            agregarEvento(
                context,
                titulo = "Entrega de compra",
                descripcion = "Tu producto llegará este día",
                inicio = fechaEntregaMillis!!,
                fin = fin
            )
            Toast.makeText(context, "Compra confirmada. Evento creado.", Toast.LENGTH_LONG).show()
            confirmarCompraPendiente = false
        } else {
            Toast.makeText(context, "Permisos de calendario requeridos", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm Purchase") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Cart")
                    }
                }
            )
        },
        bottomBar = {
            Bars2(navHostController, cartViewModel)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Total: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)

            Text("Select Payment Method:", style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { paymentMethod = "Cash" }
                ) {
                    RadioButton(
                        selected = paymentMethod == "Cash",
                        onClick = { paymentMethod = "Cash" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Cash")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { paymentMethod = "Card" }
                ) {
                    RadioButton(
                        selected = paymentMethod == "Card",
                        onClick = { paymentMethod = "Card" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Card")
                }
            }

            if (paymentMethod == "Card") {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16 && it.all { c -> c.isDigit() }) {
                            cardNumber = it
                        }
                    },
                    label = { Text("Card Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cardNumber.isNotEmpty() && !isCardValid,
                    modifier = Modifier.fillMaxWidth()
                )
                if (cardNumber.isNotEmpty() && !isCardValid) {
                    Text("Card must have 16 digits", color = MaterialTheme.colorScheme.error)
                }
            }

            Button(
                onClick = {
                    val hoy = Calendar.getInstance()
                    hoy.add(Calendar.DAY_OF_YEAR, 3)

                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val cal = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth, 10, 0)
                            }
                            fechaEntregaMillis = cal.timeInMillis
                            fechaEntregaTexto = "$dayOfMonth/${month + 1}/$year"
                        },
                        hoy.get(Calendar.YEAR),
                        hoy.get(Calendar.MONTH),
                        hoy.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (fechaEntregaTexto.isNotEmpty()) "📅 Entrega: $fechaEntregaTexto" else "📅 Seleccionar fecha de entrega")
            }

            Button(
                onClick = {
                    if (paymentMethod == "Card" && !isCardValid) {
                        Toast.makeText(context, "Please enter a valid 16-digit card number.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (fechaEntregaMillis == null) {
                        Toast.makeText(context, "Selecciona la fecha de entrega", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    confirmarCompraPendiente = true
                    launcherPermisoCalendario.launch(
                        arrayOf(
                            Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR
                        )
                    )
                },
                enabled = paymentMethod == "Cash" || isCardValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm")
            }

        }
    }
}

fun agregarEvento(context: Context, titulo: String, descripcion: String, inicio: Long, fin: Long) {
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, inicio)
        put(CalendarContract.Events.DTEND, fin)
        put(CalendarContract.Events.TITLE, titulo)
        put(CalendarContract.Events.DESCRIPTION, descripcion)
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
    }
    context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
}
