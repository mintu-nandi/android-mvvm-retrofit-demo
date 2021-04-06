package com.example.demo.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.api.status.Result
import com.example.demo.model.response.InvestorProducts
import com.example.demo.model.response.Product
import com.example.demo.util.SharedPreferencesUtil
import com.example.demo.util.SharedPreferencesUtil.clearValues
import com.example.demo.util.SharedPreferencesUtil.username
import com.example.demo.util.toast
import com.example.demo.view.LoginActivity
import com.example.demo.view.adapter.HomeAdapter
import com.example.demo.view.adapter.OnItemClickListener
import com.example.demo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.accounts_fragment.*

@AndroidEntryPoint
class AccountsFragment: Fragment(R.layout.accounts_fragment), OnItemClickListener {

    lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()
    lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initView()
        initDataObserver()
    }

    override fun onResume() {
        super.onResume()
        this.context?.let { viewModel.getInvestorProductsData(it) }
    }

    private fun initView() {
        adapter = HomeAdapter(listOf(), this)

        val layoutManager = LinearLayoutManager(this.context)
        val orientation = layoutManager.orientation
        val decoration = DividerItemDecoration(this.context, orientation)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = layoutManager
        recycler_view.addItemDecoration(decoration)
        signout.setOnClickListener {
            this.activity?.run {
                SharedPreferencesUtil.sharedPreference(it.context).clearValues
                startActivity(Intent(activity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun initDataObserver() {
        viewModel.getInvestorProducts().observe(viewLifecycleOwner, Observer {result ->
            when(result) {
                is Result.Loading -> {
                    displayProgressBar(true)
                }
                is Result.Success -> {
                    updateAdapter(result.data.products)
                    updateUI(result.data)
                    displayProgressBar(false)
                }
                is Result.Error -> {
                    displayProgressBar(false)
                    context?.toast(result.message)
                }
            }
        })
    }

    private fun updateUI(data : InvestorProducts) {
        totalPlanAmount.text = "£${data.totalPlanValue}"
        userName.text = this.context?.let { SharedPreferencesUtil.sharedPreference(it).username }
    }

    private fun updateAdapter(data: List<Product>) {
        adapter.apply {
            addData(data)
            notifyDataSetChanged()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progressBar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onItemClick(data: Product) {
        val bundle = bundleOf(INVESTOR_PRODUCT_ID to data)
        navController.navigate(R.id.action_accountsFragment_to_individualAccountFragment, bundle)
    }
}