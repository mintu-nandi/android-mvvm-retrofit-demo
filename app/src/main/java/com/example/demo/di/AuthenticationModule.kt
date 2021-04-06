package com.example.demo.di

import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.AuthenticationDataSource
import com.example.demo.repository.AuthenticationRepository
import com.example.demo.viewmodel.AuthenticationViewModel
import com.example.demo.viewmodel.factory.VMAuthenticationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AuthenticationModule {

    @ActivityScoped
    @Provides
    fun provideAuthenticationDataSource(apiService: ApiService): AuthenticationDataSource {
        return AuthenticationDataSource(apiService)
    }

    @ActivityScoped
    @Provides
    fun provideAuthenticationRepository(authenticationDataSource: AuthenticationDataSource): AuthenticationRepository {
        return AuthenticationRepository(
            authenticationDataSource
        )
    }

    @ActivityScoped
    @Provides
    fun provideAuthenticationFactory(authenticationRepository: AuthenticationRepository): VMAuthenticationFactory {
        return VMAuthenticationFactory(authenticationRepository)
    }

    @ActivityScoped
    @Provides
    fun provideAuthenticationViewModel(authenticationRepository: AuthenticationRepository): AuthenticationViewModel {
        return AuthenticationViewModel(authenticationRepository)
    }
}