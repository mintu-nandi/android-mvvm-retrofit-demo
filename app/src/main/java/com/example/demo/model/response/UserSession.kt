package com.example.demo.model.response

import com.google.gson.annotations.SerializedName

data class UserSession(
    @SerializedName("Session")
    val session: Session
)

data class Session(
    @SerializedName("BearerToken")
    val token: String
)