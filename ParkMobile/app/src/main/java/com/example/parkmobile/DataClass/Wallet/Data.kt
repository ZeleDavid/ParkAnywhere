package com.example.parkmobile.DataClass.Wallet


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("balance")
    val balance: Long,
    @SerializedName("isDelegate")
    val isDelegate: Boolean,
    @SerializedName("publicKey")
    val publicKey: Any,
    @SerializedName("secondPublicKey")
    val secondPublicKey: Any,
    @SerializedName("username")
    val username: Any,
    @SerializedName("vote")
    val vote: Any
)