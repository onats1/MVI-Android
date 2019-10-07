package com.example.mviarchitecture.di

import com.example.mviarchitecture.remoteDB.ApiService

import com.example.mviarchitecture.repository.Repository
import com.example.mviarchitecture.utils.BASE_URL
import com.example.mviarchitecture.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppRepository(apiService: ApiService): Repository{
        return Repository(apiService)
    }

}