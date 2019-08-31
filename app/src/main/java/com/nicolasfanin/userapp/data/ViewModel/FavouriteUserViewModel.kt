package com.nicolasfanin.userapp.data.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nicolasfanin.userapp.data.managers.FavouriteUserDatabase
import com.nicolasfanin.userapp.data.model.FavouriteUser
import com.nicolasfanin.userapp.data.repository.FavouriteUserRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteUserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavouriteUserRespository
    val allUsers: LiveData<List<FavouriteUser>>

    init {
        val database = FavouriteUserDatabase.getUserAppDatabase(application.applicationContext, viewModelScope)!!
        val favouriteUserDao = database.userDao()
        repository = FavouriteUserRespository(favouriteUserDao)
        allUsers = repository.allUsers
    }

    //Wrapper insert method.
    fun insert(favouriteUser: FavouriteUser) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(favouriteUser)
    }
}