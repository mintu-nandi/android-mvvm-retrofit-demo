package com.example.demo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.repository.PaymentDetailsRepository
import javax.inject.Inject

class VMPaymentDetailsFactory @Inject constructor(private val paymentDetailsRepository: PaymentDetailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PaymentDetailsRepository::class.java)
            .newInstance(paymentDetailsRepository)
    }
}