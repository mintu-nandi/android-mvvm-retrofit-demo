package com.example.demo.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.demo.R
import com.example.demo.api.status.Result
import com.example.demo.model.request.OneOffPayment
import com.example.demo.model.response.PaymentDetails
import com.example.demo.model.response.Product
import com.example.demo.util.toast
import com.example.demo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.individual_account_fragment.*

const val INVESTOR_PRODUCT_ID = "product_id"

@AndroidEntryPoint
class IndividualAccountFragment: Fragment(R.layout.individual_account_fragment) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable(INVESTOR_PRODUCT_ID)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initDataObserver()
    }

    private fun initView(view: View) {
        populateView()
        add.setOnClickListener {
            viewModel.getPaymentDetailsData(OneOffPayment(10.0, product.id))
        }
    }

    private fun populateView() {
        type.text = product.productInfo.name
        amount.text = "£${product.planValue}"
        totalamount.text = "£${product.moneybox}"
    }

    private fun initDataObserver() {
        viewModel.getPaymentDetails().observe(viewLifecycleOwner, Observer {result ->
            when(result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    updateUI(result.data)
                }
                is Result.Error -> {
                    context?.toast(result.message)
                }
            }
        })
    }

    private fun updateUI(data: PaymentDetails) {
        totalamount.text = "£${data.moneybox}"
        context?.toast("Successfully £10 added")
    }
}