package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.data.Model.UserPreferences
import com.example.zenithwear.data.Model.UserProfile
import com.example.zenithwear.data.Model.network.RetrofitClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navHostController: NavHostController) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    var showErrors by remember { mutableStateOf(false) }
    var isCheckingUsername by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                "Create your account",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it.trim()
                    usernameError = null
                },
                label = { Text("Username") },
                isError = usernameError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && (username.isBlank() || username.length < 4)) {
                Text("Username must be at least 4 characters", color = Color.Red, fontSize = 12.sp)
            }
            if (usernameError != null) {
                Text(usernameError ?: "", color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = { Text("Password") },
                isError = passwordError != null,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (showErrors && (password.isBlank() || password.length < 6)) {
                Text("Password must be at least 6 characters", color = Color.Red, fontSize = 12.sp)
            }
            if (passwordError != null) {
                Text(passwordError ?: "", color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                label = { Text("Confirm Password") },
                isError = confirmPasswordError != null,
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(icon, contentDescription = "Toggle confirm password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (showErrors && confirmPassword != password) {
                Text("Passwords do not match", color = Color.Red, fontSize = 12.sp)
            }
            if (confirmPasswordError != null) {
                Text(confirmPasswordError ?: "", color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Datos personales
        item {
            PersonalDataInputs(
                name = name,
                onNameChange = { name = it },
                lastName = lastName,
                onLastNameChange = { lastName = it },
                address = address,
                onAddressChange = { address = it },
                phoneNumber = phoneNumber,
                onPhoneNumberChange = { phoneNumber = it },
                dateOfBirth = dateOfBirth,
                onDateOfBirthChange = { dateOfBirth = it },
                gender = gender,
                onGenderChange = { gender = it },
                showErrors = showErrors
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        showErrors = true
                        usernameError = null
                        passwordError = null
                        confirmPasswordError = null

                        // Validaciones
                        if (username.isBlank() || username.length < 4) return@launch
                        if (password.isBlank() || password.length < 6) return@launch
                        if (confirmPassword != password) return@launch
                        if (name.isBlank() || lastName.isBlank() || address.isBlank()
                            || phoneNumber.isBlank() || dateOfBirth.isBlank() || gender.isBlank()
                        ) return@launch

                        isCheckingUsername = true

                        // Revisar si usuario ya existe
                        try {
                            val response = RetrofitClient.apiService.getUsers()
                            if (response.isSuccessful) {
                                val users = response.body() ?: emptyList<UserProfile>()
                                val exists = users.any { it.user.equals(username, ignoreCase = true) }
                                if (exists) {
                                    usernameError = "Username already exists"
                                    isCheckingUsername = false
                                    return@launch
                                }
                            } else {
                                usernameError = "Error checking username availability"
                                isCheckingUsername = false
                                return@launch
                            }
                        } catch (e: Exception) {
                            usernameError = "Network error: ${e.localizedMessage}"
                            isCheckingUsername = false
                            return@launch
                        }

                        isCheckingUsername = false
                        isSaving = true

                        // Crear UserProfile para enviar (id 0 o cualquier valor que backend ignore)
                        val newUser = UserProfile(
                            id = 0,
                            name = name,
                            lastName = lastName,
                            address = address,
                            phoneNumber = phoneNumber,
                            dateOfBirth = dateOfBirth,
                            gender = gender,
                            password = password,
                            user = username
                        )

                        try {
                            val createResponse = RetrofitClient.apiService.createUser(newUser)
                            if (createResponse.isSuccessful) {
                                userPreferences.saveUsername(username)
                                isSuccess = true
                                isSaving = false

                                kotlinx.coroutines.delay(1000)
                                navHostController.navigate("Profile") {
                                    popUpTo("SignUpScreen") { inclusive = true }
                                }
                            } else {
                                val errorBody = createResponse.errorBody()?.string()
                                usernameError = "Error creating account: ${createResponse.code()}- $errorBody"
                                isSaving = false
                            }
                        } catch (e: Exception) {
                            usernameError = "Network error: ${e.localizedMessage}"
                            isSaving = false
                        }
                    }
                },
                enabled = !isCheckingUsername && !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isCheckingUsername || isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text("Create Account")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (isSuccess) {
                Text("Account created successfully!", color = Color.Green)
            }
        }
    }
}

@Composable
fun PersonalDataInputs(
    name: String,
    onNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    dateOfBirth: String,
    onDateOfBirthChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    showErrors: Boolean,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("First Name") },
        isError = showErrors && name.isBlank(),
        modifier = Modifier.fillMaxWidth()
    )
    if (showErrors && name.isBlank()) {
        Text("First name is required", color = Color.Red, fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChange,
        label = { Text("Last Name") },
        isError = showErrors && lastName.isBlank(),
        modifier = Modifier.fillMaxWidth()
    )
    if (showErrors && lastName.isBlank()) {
        Text("Last name is required", color = Color.Red, fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = address,
        onValueChange = onAddressChange,
        label = { Text("Address") },
        isError = showErrors && address.isBlank(),
        modifier = Modifier.fillMaxWidth()
    )
    if (showErrors && address.isBlank()) {
        Text("Address is required", color = Color.Red, fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = phoneNumber,
        onValueChange = onPhoneNumberChange,
        label = { Text("Phone Number") },
        isError = showErrors && phoneNumber.isBlank(),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
    if (showErrors && phoneNumber.isBlank()) {
        Text("Phone number is required", color = Color.Red, fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = dateOfBirth,
        onValueChange = onDateOfBirthChange,
        label = { Text("Date of Birth") },
        isError = showErrors && dateOfBirth.isBlank(),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    if (showErrors && dateOfBirth.isBlank()) {
        Text("Date of birth is required", color = Color.Red, fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))

    val genderOptions = listOf("Male", "Female", "Other")
    Text("Gender", modifier = Modifier.padding(bottom = 4.dp))
    Row {
        genderOptions.forEach { option ->
            Row(
                Modifier
                    .selectable(
                        selected = (option == gender),
                        onClick = { onGenderChange(option) }
                    )
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == gender),
                    onClick = { onGenderChange(option) }
                )
                Text(option)
            }
        }
    }
    if (showErrors && gender.isBlank()) {
        Text("Gender is required", color = Color.Red, fontSize = 12.sp)
    }
}
