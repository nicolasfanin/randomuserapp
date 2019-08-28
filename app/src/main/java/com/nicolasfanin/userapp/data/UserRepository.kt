package com.nicolasfanin.userapp.data

import com.nicolasfanin.userapp.data.model.Result
import io.reactivex.Observable

class UserRepository(val apiService: RandomUserService) {

    fun getUsers(): Observable<Result> {
        return apiService.getUsers()
    }

}
