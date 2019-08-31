package com.nicolasfanin.userapp.data.managers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nicolasfanin.userapp.data.dao.FavouriteUserDao
import com.nicolasfanin.userapp.data.model.FavouriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [FavouriteUser::class], version = 1, exportSchema = false)
abstract class FavouriteUserDatabase : RoomDatabase() {

    abstract fun userDao(): FavouriteUserDao

    companion object {
        var INSTANCE: FavouriteUserDatabase? = null

        @JvmStatic
        fun getUserAppDatabase(context: Context): FavouriteUserDatabase? {
            if (INSTANCE == null) {
                synchronized(FavouriteUserDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            FavouriteUserDatabase::class.java,
                            "CarParkDatabase"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addMigrations()
                            .build()
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
