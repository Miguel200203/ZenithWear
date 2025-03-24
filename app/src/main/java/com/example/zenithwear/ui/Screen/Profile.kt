package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.ui.Component.CartViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.zenithwear.data.Model.UserProfile
import coil.compose.AsyncImage
import com.example.zenithwear.R

@Composable
fun Profile(navHostController: NavHostController,cartViewModel: CartViewModel){
    var user by remember {
        mutableStateOf(
            UserProfile(
                name = "John",
                lastName = "Doe",
                address = "123 Main Street",
                phoneNumber = "+1 234 567 890",
                dateOfBirth = "01/01/1990",
                gender = "Male"
            )
        )
    }

    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Bars(navHostController) },
        bottomBar = { Bars2(navHostController, cartViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // **Foto de perfil**
            AsyncImage(
                model = R.drawable.fotoperfil,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // **Nombre del usuario**
            Text(
                text = "${user.name} ${user.lastName}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // **Tarjeta con los datos del usuario**
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileInfoItem(label = "Address", value = user.address)
                    ProfileInfoItem(label = "Phone", value = user.phoneNumber)
                    ProfileInfoItem(label = "Date of Birth", value = user.dateOfBirth)
                    ProfileInfoItem(label = "Gender", value = user.gender)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // **Botón para editar perfil**
            Button(
                onClick = { isEditing = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Edit Profile")
            }
        }
    }

    if (isEditing) {
        EditUserProfileDialog(
            user = user,
            onDismiss = { isEditing = false },
            onSave = { updatedUser ->
                user = updatedUser
                isEditing = false
            }
        )
    }
}
@Composable
fun UserProfileDetails(user: UserProfile) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${user.name}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Last Name: ${user.lastName}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Address: ${user.address}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Phone: ${user.phoneNumber}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Date of Birth: ${user.dateOfBirth}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Gender: ${user.gender}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}

// **Diálogo para editar el perfil**
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserProfileDialog(user: UserProfile, onDismiss: () -> Unit, onSave: (UserProfile) -> Unit) {
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
            Button(
                onClick = {
                    onSave(UserProfile(name, lastName, address, phoneNumber, dateOfBirth, gender))
                }
            ) {
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
// **Elemento de información del perfil**
@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}