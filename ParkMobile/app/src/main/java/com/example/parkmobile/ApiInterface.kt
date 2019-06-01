package com.example.parkmobile

import retrofit2.http.*
import java.util.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface ApiInterface {

    @GET("locations/")
    fun vrniVsaParkirisca():Observable<List<Parkirisce>>

    companion object{
        fun create(): ApiInterface{
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://45.77.58.205:8000/parkchain/")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}