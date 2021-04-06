package com.example.demo.di

import com.example.demo.DemoApp
import com.example.demo.api.RetrofitBuilder
import com.example.demo.api.apiservice.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAppContext(): DemoApp {
        return DemoApp()
    }

    @Singleton
    @Provides
    fun provideApiServiceInterface(): ApiService {
        return RetrofitBuilder.apiService
    }
}
