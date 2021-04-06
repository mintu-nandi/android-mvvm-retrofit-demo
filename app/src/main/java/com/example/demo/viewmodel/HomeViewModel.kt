package com.example.demo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.api.status.Result
import com.example.demo.model.request.OneOffPayment
import com.example.demo.model.response.InvestorProducts
import com.example.demo.model.response.PaymentDetails
import com.example.demo.repository.HomeRepository
import com.example.demo.util.SharedPreferencesUtil
import com.example.demo.util.SharedPreferencesUtil.token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private val _products: MutableLiveData<Result<InvestorProducts>> = MutableLiveData()
    private val _payment: MutableLiveData<Result<PaymentDetails>> = MutableLiveData()

    fun getPaymentDetails(): LiveData<Result<PaymentDetails>> = _payment
    fun getInvestorProducts(): LiveData<Result<InvestorProducts>> = _products

    fun getInvestorProductsData(context: Context) = viewModelScope.launch {
        _products.value = Result.Loading
        _products.value = repository.getInvestorProducts(getToken(context))
    }

    fun getPaymentDetailsData(context: Context, payment: OneOffPayment) = viewModelScope.launch {
        _payment.value = Result.Loading
        _payment.value = repository.getPaymentDetails(getToken(context), payment)
    }

    private fun getToken(context: Context) =  "Bearer "+ SharedPreferencesUtil.sharedPreference(context).token
}