package com.example.demo.di

import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.PaymentDetailsDataSource
import com.example.demo.repository.PaymentDetailsRepository
import com.example.demo.viewmodel.PaymentDetailsViewModel
import com.example.demo.viewmodel.factory.VMPaymentDetailsFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object PaymentDetailsModule {
    @ActivityScoped
    @Provides
    fun providePaymentDetailsDataSource(apiService: ApiService): PaymentDetailsDataSource {
        return PaymentDetailsDataSource(apiService)
    }

    @ActivityScoped
    @Provides
    fun providePaymentDetailsRepository(paymentDetailsDataSource: PaymentDetailsDataSource): PaymentDetailsRepository {
        return PaymentDetailsRepository(
            paymentDetailsDataSource
        )
    }

    @ActivityScoped
    @Provides
    fun provideVMPaymentDetailsFactory(paymentDetailsRepository: PaymentDetailsRepository): VMPaymentDetailsFactory {
        return VMPaymentDetailsFactory(paymentDetailsRepository)
    }

    @ActivityScoped
    @Provides
    fun providePaymentDetailsViewModel(paymentDetailsRepository: PaymentDetailsRepository): PaymentDetailsViewModel {
        return PaymentDetailsViewModel(paymentDetailsRepository)
    }
}