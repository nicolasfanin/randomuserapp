package com.nicolasfanin.userapp.data.repository

import com.nicolasfanin.userapp.data.RandomUserService
import com.nicolasfanin.userapp.data.model.Result
import io.reactivex.Observable

class UserRepository(val apiService: RandomUserService) {

    fun getUsers(): Observable<Result> {
        return apiService.getUsers()
    }

    fun getPaginationUsers(page: Int, seed: String): Observable<Result> {
        return apiService.getPaginationUsers(page, seed)
    }

}
