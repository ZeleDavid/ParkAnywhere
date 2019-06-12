package com.example.parkmobile.DataClass.Wallet

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("data")
    val data: Data
)