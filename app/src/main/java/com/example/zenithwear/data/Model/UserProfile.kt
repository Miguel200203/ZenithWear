package com.example.zenithwear.data.Model


data class UserProfile(
    val id: Int? = null,
    val name: String,
    val lastName: String,
    val address: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val gender: String,
    val password: String,
    val user: String // <- este es el nombre de usuario
)