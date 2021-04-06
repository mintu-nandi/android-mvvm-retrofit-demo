package com.example.demo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.model.response.Product

class HomeAdapter (private var users: List<Product>, private val listener: OnItemClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: Product, listener: OnItemClickListener) {
            itemView.findViewById<TextView>(R.id.account).text = product.productInfo.name
            itemView.findViewById<TextView>(R.id.planValue).text = "Plan Value: £${product.planValue}"
            itemView.findViewById<TextView>(R.id.moneybox).text = "Moneybox: £${product.moneybox}"

            itemView.setOnClickListener {
                listener.onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindView(users[position], listener)
    }

    fun addData(user: List<Product>) {
        users = user
    }
}

interface OnItemClickListener {
    fun onItemClick(data: Product)
}