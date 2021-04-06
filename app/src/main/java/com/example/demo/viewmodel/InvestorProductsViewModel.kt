package com.example.demo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.api.status.Result
import com.example.demo.model.response.InvestorProducts
import com.example.demo.repository.InvestorProductsRepository
import com.example.demo.util.SharedPreferencesUtil
import com.example.demo.util.SharedPreferencesUtil.token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestorProductsViewModel @Inject constructor(private val investorProductsRepository: InvestorProductsRepository) :
    ViewModel() {

    private val _products: MutableLiveData<Result<InvestorProducts>> = MutableLiveData()

    fun getInvestorProducts(): LiveData<Result<InvestorProducts>> = _products
    fun getInvestorProductsData(context: Context) = viewModelScope.launch {

        _products.value = Result.Loading
        _products.value = investorProductsRepository.getInvestorProducts(getToken(context))
    }

    private fun getToken(context: Context) =  "Bearer "+ SharedPreferencesUtil.sharedPreference(context).token
}