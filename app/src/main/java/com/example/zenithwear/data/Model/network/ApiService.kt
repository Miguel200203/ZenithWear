package com.example.zenithwear.data.Model.network

import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.Brand
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("api/brands")
    suspend fun getBrands(): Response<List<Brand>>
}