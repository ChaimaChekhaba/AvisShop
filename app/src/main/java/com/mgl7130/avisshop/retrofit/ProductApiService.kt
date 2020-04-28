package com.mgl7130.avisshop.retrofit

import com.mgl7130.avisshop.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("HttpProductDetails")
    fun getData(@Query("name") barcode : String) : Call<Product>
}