package com.example.demo.api.datasource

import com.example.demo.api.apiservice.ApiService
import com.example.demo.model.request.UserCredential
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAuthenticationData(user: UserCredential) = apiService.authentication(user)
}