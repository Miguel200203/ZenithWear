package com.example.zenithwear.data.Model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitClient {
    private const val BASE_URL = "https://zenithwear.rodrigocarreon.com/api/"

    private val gson = GsonBuilder()
        .setLenient()  // Permite JSON malformado o irregularidades leves
        .create()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
