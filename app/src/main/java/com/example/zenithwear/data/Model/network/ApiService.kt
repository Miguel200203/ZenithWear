package com.example.zenithwear.data.Model.network

import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.Brand
import com.example.zenithwear.data.Model.Category
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("brands")
    suspend fun getBrands(): Response<List<Brand>>

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>
}