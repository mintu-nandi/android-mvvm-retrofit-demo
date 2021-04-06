package com.example.demo.api.apiservice

import com.example.demo.model.request.OneOffPayment
import com.example.demo.model.request.UserCredential
import com.example.demo.model.response.InvestorProducts
import com.example.demo.model.response.PaymentDetails
import com.example.demo.model.response.UserSession
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun authentication(@Body userLogin: UserCredential): UserSession

    @GET("investorproducts")
    suspend fun getInvestorProducts(@Header("Authorization") token: String): InvestorProducts

    @POST("oneoffpayments")
    suspend fun addPayment(
        @Header("Authorization") token: String,
        @Body payment: OneOffPayment
    ): PaymentDetails
}