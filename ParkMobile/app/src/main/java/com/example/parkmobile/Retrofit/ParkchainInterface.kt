package com.example.parkmobile.Retrofit

import com.example.parkmobile.DataClass.Parkirisce
import com.example.parkmobile.DataClass.StarterpackResponse
import retrofit2.http.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface ParkchainInterface {
    //parkchain
    @GET("locations")
    fun vrniVsaParkirisca():Observable<List<Parkirisce>>

    @GET("starterpack/{address}")
    fun starterpack(@Path("address") address: String):Observable<StarterpackResponse>

    @GET("location/{id}")
    fun vrniParkirisce(@Path("id") id: String): Observable<Parkirisce>

    companion object{
        fun create(): ParkchainInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://45.77.58.205:8000/parkchain/")
                .build()

            return retrofit.create(ParkchainInterface::class.java)
        }
    }
}