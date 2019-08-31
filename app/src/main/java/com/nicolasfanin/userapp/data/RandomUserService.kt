package com.nicolasfanin.userapp.data

import com.nicolasfanin.userapp.data.model.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("?results=12")
    fun getUsers(): Observable<Result>

    @GET("?results=3")
    fun getPaginationUsers(@Query("page") page: Int, @Query("seed") seed: String): Observable<Result>

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
