package com.example.parkmobile.DataClass.Transactions


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("blockId")
    val blockId: String,
    @SerializedName("confirmations")
    val confirmations: Int,
    @SerializedName("fee")
    val fee: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("recipient")
    val recipient: String,
    @SerializedName("sender")
    val sender: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("vendorField")
    val vendorField: String,
    @SerializedName("timestamp")
    val timestamp: Timestamp,
    @SerializedName("type")
    val type: Int,
    @SerializedName("version")
    val version: Int
)