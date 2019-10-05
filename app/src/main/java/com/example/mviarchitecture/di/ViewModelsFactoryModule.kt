package com.example.mviarchitecture.di

import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecture.di.viewModels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelsFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory


}