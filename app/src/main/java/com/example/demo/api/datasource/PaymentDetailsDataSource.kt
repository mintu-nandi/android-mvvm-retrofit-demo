package com.example.demo.api.datasource

import com.example.demo.api.apiservice.ApiService
import com.example.demo.model.request.OneOffPayment
import javax.inject.Inject

class PaymentDetailsDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getPaymentDetails(token: String, payment: OneOffPayment) = apiService.addPayment(token, payment)
}