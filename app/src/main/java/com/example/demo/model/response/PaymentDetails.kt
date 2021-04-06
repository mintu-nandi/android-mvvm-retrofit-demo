package com.example.demo.model.response

import com.google.gson.annotations.SerializedName

data class PaymentDetails(
    @SerializedName("Moneybox")
    val moneybox: Double,
    @SerializedName("Name")
    val name: String,
    @SerializedName("message")
    val message: String
)