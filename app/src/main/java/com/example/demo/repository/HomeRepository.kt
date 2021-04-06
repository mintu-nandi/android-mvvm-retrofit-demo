package com.example.demo.repository

import com.example.demo.api.datasource.HomeDataSource
import com.example.demo.model.request.OneOffPayment
import com.example.demo.repository.base.BaseRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class HomeRepository @Inject constructor(
    private val dataSource: HomeDataSource
) : BaseRepository() {
    suspend fun getInvestorProducts(token: String) = safeApiCall { dataSource.getInvestorProducts(token) }

    suspend fun getPaymentDetails(token: String, payment: OneOffPayment) =
        safeApiCall { dataSource.getPaymentDetails(token, payment) }
}