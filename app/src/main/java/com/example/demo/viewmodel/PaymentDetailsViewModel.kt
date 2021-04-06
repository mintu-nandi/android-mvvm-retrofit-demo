package com.example.demo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.api.status.Result
import com.example.demo.model.request.OneOffPayment
import com.example.demo.model.response.PaymentDetails
import com.example.demo.repository.PaymentDetailsRepository
import com.example.demo.util.SharedPreferencesUtil
import com.example.demo.util.SharedPreferencesUtil.token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentDetailsViewModel @Inject constructor(private val paymentDetailsRepository: PaymentDetailsRepository) :
    ViewModel() {
    private val _payment: MutableLiveData<Result<PaymentDetails>> = MutableLiveData()

    fun getPaymentDetails(): LiveData<Result<PaymentDetails>> = _payment
    fun getPaymentDetailsData(context: Context, payment: OneOffPayment) = viewModelScope.launch {

        _payment.value = Result.Loading
        _payment.value = paymentDetailsRepository.getPaymentDetails(getToken(context), payment)
    }

    private fun getToken(context: Context) =  "Bearer "+ SharedPreferencesUtil.sharedPreference(context).token
}