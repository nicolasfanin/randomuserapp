package com.nicolasfanin.userapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteUser (

    @PrimaryKey(autoGenerate = false)
    var userId: String,
    val completedUserName: String,
    val pictureThumbnail: String

)
