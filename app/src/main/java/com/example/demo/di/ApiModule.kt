package com.example.demo.di

import android.content.Context
import android.content.SharedPreferences
import com.example.demo.DemoApp
import com.example.demo.api.RetrofitBuilder
import com.example.demo.api.apiservice.ApiService
import com.example.demo.util.SharedPreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPreferencesUtil.preferencesName, Context.MODE_PRIVATE)
    }
}
