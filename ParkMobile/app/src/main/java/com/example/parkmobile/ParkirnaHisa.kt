package com.example.parkmobile


import com.google.gson.annotations.SerializedName

data class ParkirnaHisa(
    @SerializedName("cenaNaUro")
    val cenaNaUro: Double,
    @SerializedName("idParkirnaHisa")
    val idParkirnaHisa: Int,
    @SerializedName("lastnik")
    val lastnik: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("naslov")
    val naslov: String,
    @SerializedName("naziv")
    val naziv: String,
    @SerializedName("stVsehMest")
    val stVsehMest: Int,
    @SerializedName("stZasedenihMest")
    val stZasedenihMest: Int
)