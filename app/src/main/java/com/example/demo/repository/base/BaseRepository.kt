package com.example.demo.repository.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.demo.api.status.Result
import com.example.demo.util.ErrorHandler
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall (apiCall: suspend() -> T) : Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (e: Exception) {
                if(e is HttpException) {
                    Result.Error(ErrorHandler.httpErrors(e))
                } else {
                    Result.Error(e.message ?: "Error Occurred!")
                }
            }
        }
    }
}