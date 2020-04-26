package com.mgl7130.avisshop.business

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder



object ServiceBuilder {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://productreview.azurewebsites.net/api/"

    // create a retrofit instance, only if it has not been created yet.
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setLenient()
                        .create()))
                    .build()
            }

            return retrofit
        }
}