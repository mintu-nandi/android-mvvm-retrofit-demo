package com.example.demo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.repository.AuthenticationRepository
import javax.inject.Inject

class VMAuthenticationFactory @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthenticationRepository::class.java)
            .newInstance(authenticationRepository)
    }
}