package com.example.demo.repository

import com.example.demo.api.datasource.PaymentDetailsDataSource
import com.example.demo.model.request.OneOffPayment
import com.example.demo.repository.base.BaseRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class PaymentDetailsRepository @Inject constructor(private val paymentDetailsDataSource: PaymentDetailsDataSource
) : BaseRepository() {
    suspend fun getPaymentDetails(token: String, payment: OneOffPayment) =
        safeApiCall { paymentDetailsDataSource.getPaymentDetails(token, payment) }
}