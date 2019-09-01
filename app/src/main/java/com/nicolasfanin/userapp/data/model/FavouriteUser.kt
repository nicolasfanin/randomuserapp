package com.nicolasfanin.userapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_user")
data class FavouriteUser (

    var gender: String,
    var nameTitle: String,
    var nameFirst: String,
    var nameLast: String,
    var email: String,
    var pictureLarge: String,
    var pictureMedium: String,
    var pictureThumbnail: String,
    @PrimaryKey(autoGenerate = false)
    var loginUuid: String,
    var loginUserName: String,
    var phone: String,
    var cell: String,
    var locationStreet: String,
    var locationCity: String,
    var locationState: String,
    var locationPostCode: String,
    var idName: String,
    var idValue: String,
    val completedUserName: String

)
