package com.example.parkmobile.DataClass.Transactions


import com.google.gson.annotations.SerializedName

data class VendorField(
    @SerializedName("cas")
    val cas: Double,
    @SerializedName("naziv")
    val naziv: String,
    @SerializedName("pId")
    val pId: String,
    @SerializedName("reg")
    val reg: String
)