package com.example.parkmobile.DataClass.Transactions


import com.google.gson.annotations.SerializedName

data class Timestamp(
    @SerializedName("epoch")
    val epoch: Int,
    @SerializedName("human")
    val human: String,
    @SerializedName("unix")
    val unix: Int
)