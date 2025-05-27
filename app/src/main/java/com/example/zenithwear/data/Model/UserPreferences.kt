package com.example.zenithwear.data.Model


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para crear DataStore en el Contexto
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val ID_KEY = intPreferencesKey("id")
        val NAME_KEY = stringPreferencesKey("name")
        val LASTNAME_KEY = stringPreferencesKey("lastName")
        val ADDRESS_KEY = stringPreferencesKey("address")
        val PHONE_KEY = stringPreferencesKey("phoneNumber")
        val DOB_KEY = stringPreferencesKey("dateOfBirth")
        val GENDER_KEY = stringPreferencesKey("gender")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val USERNAME_KEY = stringPreferencesKey("user")
    }


    suspend fun saveUser(user: com.example.zenithwear.data.Model.UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[ID_KEY] = user.id ?: -1
            prefs[NAME_KEY] = user.name
            prefs[LASTNAME_KEY] = user.lastName
            prefs[ADDRESS_KEY] = user.address
            prefs[PHONE_KEY] = user.phoneNumber
            prefs[DOB_KEY] = user.dateOfBirth
            prefs[GENDER_KEY] = user.gender
            prefs[PASSWORD_KEY] = user.password
            prefs[USERNAME_KEY] = user.user
        }
    }

    val userFlow: Flow<com.example.zenithwear.data.Model.UserProfile?> = context.dataStore.data
        .map { prefs ->
            val id = prefs[ID_KEY] ?: return@map null
            val name = prefs[NAME_KEY] ?: ""
            val lastName = prefs[LASTNAME_KEY] ?: ""
            val address = prefs[ADDRESS_KEY] ?: ""
            val phone = prefs[PHONE_KEY] ?: ""
            val dob = prefs[DOB_KEY] ?: ""
            val gender = prefs[GENDER_KEY] ?: ""
            val password = prefs[PASSWORD_KEY] ?: ""
            val user = prefs[USERNAME_KEY] ?: ""

            com.example.zenithwear.data.Model.UserProfile(
                id = id,
                name = name,
                lastName = lastName,
                address = address,
                phoneNumber = phone,
                dateOfBirth = dob,
                gender = gender,
                password = password,
                user = user
            )
        }
    suspend fun saveUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }

    val usernameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY]
        }
}
