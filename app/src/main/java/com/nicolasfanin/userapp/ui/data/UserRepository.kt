package com.nicolasfanin.userapp.ui.data

import com.nicolasfanin.userapp.ui.data.model.Result
import io.reactivex.Observable

class UserRepository(val apiService: RandomUserService) {

    fun getUsers(): Observable<Result> {
        return apiService.getUsers()
    }

}
