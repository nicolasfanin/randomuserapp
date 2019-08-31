package com.nicolasfanin.userapp.data.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicolasfanin.userapp.data.model.FavouriteUser

@Dao
interface FavouriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM favourite_user")
    fun getUsers() : List<FavouriteUser>

    @Query("SELECT * FROM favourite_user")
    fun getAllUsers(): LiveData<List<FavouriteUser>>
}
