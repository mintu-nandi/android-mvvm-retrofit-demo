package com.example.demo.repository

import com.example.demo.api.datasource.InvestorProductsDataSource
import com.example.demo.repository.base.BaseRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class InvestorProductsRepository @Inject constructor(
    private val investorProductsDataSource: InvestorProductsDataSource
) : BaseRepository() {
    suspend fun getInvestorProducts(token: String) = safeApiCall { investorProductsDataSource.getInvestorProducts(token) }
}