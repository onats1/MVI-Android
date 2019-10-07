package com.example.mviarchitecture.di.main

import com.example.mviarchitecture.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributesMainFragment(): MainFragment

}