package com.example.demo.di

import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.HomeDataSource
import com.example.demo.repository.HomeRepository
import com.example.demo.viewmodel.HomeViewModel
import com.example.demo.viewmodel.factory.VMHomeFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object HomeModule {
    @ActivityScoped
    @Provides
    fun provideHomeDataSource(apiService: ApiService): HomeDataSource {
        return HomeDataSource(apiService)
    }

    @ActivityScoped
    @Provides
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
        return HomeRepository(
            homeDataSource
        )
    }

    @ActivityScoped
    @Provides
    fun provideVMHomeFactory(homeRepository: HomeRepository): VMHomeFactory {
        return VMHomeFactory(homeRepository)
    }

    @ActivityScoped
    @Provides
    fun provideHomeViewModel(homeRepository: HomeRepository): HomeViewModel {
        return HomeViewModel(homeRepository)
    }
}