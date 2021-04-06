package com.example.demo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.repository.InvestorProductsRepository
import javax.inject.Inject

class VMInvestorProductsFactory @Inject constructor(private val investorProductsRepository: InvestorProductsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(InvestorProductsRepository::class.java)
            .newInstance(investorProductsRepository)
    }
}