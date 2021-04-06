package com.example.demo.repository

import com.example.demo.api.datasource.AuthenticationDataSource
import com.example.demo.model.request.UserCredential
import com.example.demo.repository.base.BaseRepository
import javax.inject.Inject

open class AuthenticationRepository @Inject constructor(private val authenticationDataSource: AuthenticationDataSource) :
    BaseRepository() {
    suspend fun getUserData(user: UserCredential) = safeApiCall { authenticationDataSource.getAuthenticationData(user) }
}