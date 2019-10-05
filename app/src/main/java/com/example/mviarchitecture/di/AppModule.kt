package com.example.mviarchitecture.di

import com.example.mviarchitecture.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppRepository(): Repository{
        return Repository
    }

}