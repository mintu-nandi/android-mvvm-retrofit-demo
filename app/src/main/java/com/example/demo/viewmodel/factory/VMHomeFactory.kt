package com.example.demo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.repository.HomeRepository
import javax.inject.Inject

class VMHomeFactory @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepository::class.java)
            .newInstance(homeRepository)
    }
}