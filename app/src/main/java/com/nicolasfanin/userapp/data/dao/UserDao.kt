package com.nicolasfanin.userapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicolasfanin.userapp.data.model.FavouriteUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM FavouriteUser")
    fun getUsers() : FavouriteUser?
}
