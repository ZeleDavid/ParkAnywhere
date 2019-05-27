package com.example.parkmobile

import retrofit2.http.*
import java.util.*
import io.reactivex.Observable

interface ApiInterface {

    @GET("parkirneHise")
    fun vrniParkirneHise():Observable<List<ParkirnaHisa>>

}