package com.example.parkmobile.DataClass.Transactions


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("count")
    val count: Int,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String,
    @SerializedName("next")
    val next: Any,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("self")
    val self: String,
    @SerializedName("totalCount")
    val totalCount: Int
)