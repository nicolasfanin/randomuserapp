package com.nicolasfanin.userapp.data.repository

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.nicolasfanin.userapp.data.dao.FavouriteUserDao
import com.nicolasfanin.userapp.data.model.FavouriteUser

class FavouriteUserRespository(private val favouriteUserDao: FavouriteUserDao) {

    val allUsers: LiveData<List<FavouriteUser>> = favouriteUserDao.getAllUsers()

    @WorkerThread
    suspend fun insert(favouriteUser: FavouriteUser){
        favouriteUserDao.insertUser(favouriteUser)
    }

}
