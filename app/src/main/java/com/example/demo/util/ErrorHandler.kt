package com.example.demo.util

import com.example.demo.model.response.ErrorData
import com.google.gson.Gson
import retrofit2.HttpException

object ErrorHandler {
    fun httpErrors(exception: HttpException): String {
        when (exception.code()) {
            400 -> {
                val error = Gson().fromJson(
                    exception.response()!!.errorBody()!!.charStream().readText(),
                    ErrorData::class.java
                )
                return error.message
            }
            401 -> {
                val error = Gson().fromJson(
                    exception.response()!!.errorBody()!!.charStream().readText(),
                    ErrorData::class.java
                )
                return error.message
            }
            else -> {
                return exception.message()
            }
        }
    }
}