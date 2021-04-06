package com.example.demo.model.response

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("ValidationErrors")
    val error: List<Error>
)

data class Error  (
    @SerializedName("Name")
    val name: String,
    @SerializedName("Message")
    val message: String
)