package com.ridvan.mobiproduct.network

import com.ridvan.mobiproduct.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface MobiProductService {
    @GET("/")
    fun getCategoryAndProducts(): Call<List<Category>>
}