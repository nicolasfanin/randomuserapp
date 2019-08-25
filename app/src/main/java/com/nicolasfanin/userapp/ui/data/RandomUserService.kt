package com.nicolasfanin.userapp.ui.data

import com.nicolasfanin.userapp.ui.data.model.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RandomUserService {

    @GET("?results=20")
    fun getUsers(): Observable<Result>

    companion object Factory {
        fun create(): RandomUserService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://randomuser.me/api/")
                .build()

            return retrofit.create(RandomUserService::class.java)
        }
    }

}