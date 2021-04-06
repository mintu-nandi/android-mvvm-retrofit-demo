package com.example.demo.api.datasource

import com.example.demo.api.apiservice.ApiService
import com.example.demo.model.request.OneOffPayment
import javax.inject.Inject

class HomeDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getInvestorProducts(token: String) = apiService.getInvestorProducts(token)
    suspend fun getPaymentDetails(token: String, payment: OneOffPayment) = apiService.addPayment(token, payment)
}