package com.example.zenithwear.data.Model.network

import com.example.zenithwear.data.Model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>

}

