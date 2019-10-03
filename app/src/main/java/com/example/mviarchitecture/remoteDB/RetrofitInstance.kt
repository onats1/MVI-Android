package com.example.mviarchitecture.remoteDB

import com.example.mviarchitecture.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://open-api.xyz/"

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    val retrofitService: ApiService by lazy {
        retrofit.build()
            .create(ApiService::class.java)
    }
}