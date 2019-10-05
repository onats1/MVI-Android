package com.example.mviarchitecture.di.main

import androidx.lifecycle.ViewModel
import com.example.mviarchitecture.di.viewModels.ViewModelKey
import com.example.mviarchitecture.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel



}