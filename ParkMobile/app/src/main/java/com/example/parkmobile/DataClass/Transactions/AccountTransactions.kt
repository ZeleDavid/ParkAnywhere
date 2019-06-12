package com.example.parkmobile.DataClass.Transactions


import com.google.gson.annotations.SerializedName

data class AccountTransactions(
    @SerializedName("data")
    val `data`: List<Transaction>,
    @SerializedName("meta")
    val meta: Meta
)