package com.example.mviarchitecture.di

import com.example.mviarchitecture.di.main.MainFragmentBuildersModule
import com.example.mviarchitecture.di.main.MainViewModelsModule
import com.example.mviarchitecture.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [MainViewModelsModule::class, MainFragmentBuildersModule::class]
    )
    abstract fun contributesMainActivity(): MainActivity
}