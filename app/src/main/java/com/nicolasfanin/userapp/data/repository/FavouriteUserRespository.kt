package com.nicolasfanin.userapp.data.repository

import android.app.Application
import com.nicolasfanin.userapp.data.dao.UserDao
import com.nicolasfanin.userapp.data.managers.FavouriteUserDatabase

class FavouriteUserRespository(application: Application) {

    private val userDao: UserDao

    init {
        val database: FavouriteUserDatabase = FavouriteUserDatabase.getUserAppDatabase(application.applicationContext)!!
        userDao = database.userDao()
    }
}
