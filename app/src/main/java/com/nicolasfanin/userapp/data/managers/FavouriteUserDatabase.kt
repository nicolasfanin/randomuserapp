package com.nicolasfanin.userapp.data.managers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nicolasfanin.userapp.data.dao.FavouriteUserDao
import com.nicolasfanin.userapp.data.model.FavouriteUser

@Database(entities = arrayOf(FavouriteUser::class), version = 1)
abstract class FavouriteUserDatabase: RoomDatabase() {

    abstract fun userDao(): FavouriteUserDao

    companion object {
        var INSTANCE: FavouriteUserDatabase? = null

        @JvmStatic
        fun getUserAppDatabase(context: Context): FavouriteUserDatabase? {
            if (INSTANCE == null) {
                synchronized(FavouriteUserDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, FavouriteUserDatabase::class.java, "CarParkDatabase")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addMigrations().build()
                }
            }
            return INSTANCE
        }

        @JvmStatic
        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}
