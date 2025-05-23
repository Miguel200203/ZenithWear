package com.example.zenithwear.data.Model.network

import com.example.zenithwear.data.Model.Product
import com.example.zenithwear.data.Model.Brand
import com.example.zenithwear.data.Model.Category
import com.example.zenithwear.data.Model.UserProfile
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("brands")
    suspend fun getBrands(): Response<List<Brand>>

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("users")
    suspend fun getUsers(): Response<List<UserProfile>>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: UserProfile
    ): Response<UserProfile>
}