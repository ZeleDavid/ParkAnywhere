package com.example.parkmobile.Retrofit

import com.example.parkmobile.DataClass.Transactions.AccountTransactions
import com.example.parkmobile.DataClass.Wallet.Wallet
import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface ApiV2Interface {
    @GET("wallets/{address}")
    fun vrniDenarnico(@Path("address") address: String): Observable<Wallet>

    @GET("wallets/{address}/transactions/sent")
    fun vrniTransakcije(@Path("address") address: String):Observable<AccountTransactions>

    companion object {
        fun create(): ApiV2Interface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("http://45.77.58.205:4003/api/v2/")
                .build()

            return retrofit.create(ApiV2Interface::class.java)
        }
    }


}