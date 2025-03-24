package com.example.zenithwear.ui.Screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun PersonalInformation(navController: NavHostController) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
            .background(color =Color.White)

    )

            {
        item { /*Bars()*/
        Text("Please provide your personal data to complete the registration",
            fontSize = (18.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp))
        }
        item {
            PersonalDataForm(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataForm(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var showErrors by remember { mutableStateOf(false) }


    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


    var isSaving by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }


    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = if (showErrors && name.isEmpty()) Color.Red else Color.Blue,
                unfocusedIndicatorColor = if (showErrors && name.isEmpty()) Color.Red else Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )
        if (showErrors && name.isEmpty()) {
            Text(
                text = "Name is required",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = if (showErrors && lastName.isEmpty()) Color.Red else Color.Blue,
                unfocusedIndicatorColor = if (showErrors && lastName.isEmpty()) Color.Red else Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )
        if (showErrors && lastName.isEmpty()) {
            Text(
                text = "Last Name is required",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = if (showErrors && address.isEmpty()) Color.Red else Color.Blue,
                unfocusedIndicatorColor = if (showErrors && address.isEmpty()) Color.Red else Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )
        if (showErrors && address.isEmpty()) {
            Text(
                text = "Address is required",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = if (showErrors && phoneNumber.isEmpty()) Color.Red else Color.Blue,
                unfocusedIndicatorColor = if (showErrors && phoneNumber.isEmpty()) Color.Red else Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )
        if (showErrors && phoneNumber.isEmpty()) {
            Text(
                text = "Phone Number is required",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("Date of Birth (MM/DD/YYYY)") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = if (showErrors && dateOfBirth.isEmpty()) Color.Red else Color.Blue,
                    unfocusedIndicatorColor = if (showErrors && dateOfBirth.isEmpty()) Color.Red else Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
        }
        if (showErrors && dateOfBirth.isEmpty()) {
            Text(
                text = "Date of Birth is required",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false

                            datePickerState.selectedDateMillis?.let { millis ->
                                val calendar = Calendar.getInstance().apply {
                                    timeInMillis = millis
                                }
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH) + 1
                                val day = calendar.get(Calendar.DAY_OF_MONTH)
                                dateOfBirth = "$month/$day/$year"
                            }
                        }
                    ) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de género
        Text("Select Gender:", modifier = Modifier.align(Alignment.Start))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderOption(
                text = "Male",
                isSelected = gender == "Male",
                onSelection = { gender = "Male" }
            )
            GenderOption(
                text = "Female",
                isSelected = gender == "Female",
                onSelection = { gender = "Female" }
            )
        }
        if (showErrors && gender.isEmpty()) {
            Text(
                text = "Gender is required",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar
        Button(
            onClick = {
                if (name.isEmpty() || lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty()) {
                    showErrors = true
                } else {
                    showErrors = false
                    isSaving = true
                    coroutineScope.launch {
                        delay(2000)
                        isSaving = false
                        isSuccess = true
                        delay(1000)
                        navController.navigate("HomePage")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Indicador de progreso
        if (isSaving) {
            CircularProgressIndicator()
        }

        // Mensaje de éxito
        if (isSuccess) {
            Text(
                text = "Data saved successfully!",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GenderOption(
    text: String,
    isSelected: Boolean,
    onSelection: () -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onSelection
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
