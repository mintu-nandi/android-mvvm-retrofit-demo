package com.example.demo.api.datasource

import com.example.demo.api.apiservice.ApiService
import javax.inject.Inject

class InvestorProductsDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getInvestorProducts(token: String) = apiService.getInvestorProducts(token)
}