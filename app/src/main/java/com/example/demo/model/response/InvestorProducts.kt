package com.example.demo.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class InvestorProducts(
    @SerializedName("TotalPlanValue")
    val totalPlanValue: Double,
    @SerializedName("ProductResponses")
    val products: List<Product>
)

@Parcelize
data class Product(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("PlanValue")
    val planValue: Double,
    @SerializedName("Moneybox")
    val moneybox: Double,
    @SerializedName("Product")
    val productInfo: ProductDetails
) : Parcelable

@Parcelize
data class ProductDetails(
    @SerializedName("FriendlyName")
    val name: String
): Parcelable