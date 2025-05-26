package com.example.zenithwear.ui.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.zenithwear.R
import com.example.zenithwear.data.Model.UserProfile
import com.example.zenithwear.data.Model.network.RetrofitClient
import com.example.zenithwear.ui.Component.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun Profile(navHostController: NavHostController, cartViewModel: CartViewModel) {
    var user by remember { mutableStateOf<UserProfile?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    LaunchedEffect(true) {
        try {
            val response = RetrofitClient.apiService.getUsers()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                user = response.body()?.first()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }
    ) { innerPadding ->
        if (user == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (isPortrait) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState()), // SCROLL AÑADIDO AQUÍ
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileContent(user, navHostController, onEditClick = { isEditing = true })
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .verticalScroll(rememberScrollState()), // SCROLL AÑADIDO AQUÍ
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ProfileContent(user, navHostController, onEditClick = { isEditing = true })
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }

    if (isEditing && user != null) {
        EditUserProfileDialog(
            user = user!!,
            onDismiss = { isEditing = false },
            onSave = { updatedUser ->
                scope.launch {
                    try {
                        val updateResponse = RetrofitClient.apiService.updateUser(user!!.id, updatedUser)
                        if (updateResponse.isSuccessful) {
                            val reloadResponse = RetrofitClient.apiService.getUsers()
                            if (reloadResponse.isSuccessful && !reloadResponse.body().isNullOrEmpty()) {
                                user = reloadResponse.body()!!.first()
                            }
                        } else {
                            println("Error al actualizar: ${updateResponse.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                isEditing = false
            }
        )
    }
}

@Composable
fun ProfileContent(user: UserProfile?, navHostController: NavHostController, onEditClick: () -> Unit) {
    Spacer(modifier = Modifier.height(20.dp))

    AsyncImage(
        model = R.drawable.fotoperfil,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "${user?.name ?: ""} ${user?.lastName ?: ""}",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileInfoItem(label = "Address", value = user!!.address ?: "")
            ProfileInfoItem(label = "Phone", value = user.phoneNumber ?: "")
            ProfileInfoItem(label = "Date of Birth", value = user.dateOfBirth ?: "")
            ProfileInfoItem(label = "Gender", value = user.gender ?: "")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onEditClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Edit Profile")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navHostController.navigate("Home_Screen") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Login")
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserProfileDialog(
    user: UserProfile,
    onDismiss: () -> Unit,
    onSave: (UserProfile) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var address by remember { mutableStateOf(user.address) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var dateOfBirth by remember { mutableStateOf(user.dateOfBirth) }
    var gender by remember { mutableStateOf(user.gender) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                OutlinedTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                OutlinedTextField(value = dateOfBirth, onValueChange = { dateOfBirth = it }, label = { Text("Date of Birth") })
                OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    UserProfile(user.id, name, lastName, address, phoneNumber, dateOfBirth, gender)
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ProfileInfoItem(label: String, value: String?) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = value ?: "Not provided", fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}
