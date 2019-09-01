package com.nicolasfanin.userapp.data.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserData(val user: User) : Serializable

data class User(
    @SerializedName("gender") val gender: String? = "no gender",
    @SerializedName("name") val name: Name?,
    @SerializedName("email") val email: String? = "no email",
    @SerializedName("picture") val picture: Picture?,
    @SerializedName("login") val login: Login?,
    @SerializedName("phone") val phone: String? = "no phone",
    @SerializedName("cell") val cell: String? = "no cell",
    @SerializedName("location") val location: Location?,
    @SerializedName("id") val id: Id?,
    var completeUserName: String? = "no complete name"
) : Serializable

data class Name(
    @SerializedName("title") val title: String? = "no title name",
    @SerializedName("first") val first: String? = "no first name",
    @SerializedName("last") val last: String? = "no last name"
) : Serializable

data class Picture(
    @SerializedName("large") val large: String? = "no large image",
    @SerializedName("medium") val medium: String? = "no medium image",
    @SerializedName("thumbnail") val thumbnail: String? = "no thumbnail image"
) : Serializable

data class Login(
    @SerializedName("uuid") val uuid: String? = "no uuid",
    @SerializedName("username") val username: String? = "no username"
) : Serializable

data class Location(
    @SerializedName("street") val street: String? = "no street",
    @SerializedName("city") val city: String? = "no city",
    @SerializedName("state") val state: String? = "no state",
    @SerializedName("postcode") val postcode: String? = "no postcode"
) : Serializable

data class Id(
    @SerializedName("name") val name: String? = "no id name",
    @SerializedName("value") val value: String? = "no value"
) : Serializable

data class ServiceInfo(
    @SerializedName("seed") val seed: String? = "no seed",
    @SerializedName("page") val page: String? = "no page"
) : Serializable

data class Result(val results: List<User>, val info: ServiceInfo)

class UserWrapper(val fUser: FavouriteUser) {

    fun getUser() : User {
        return User(
            fUser.gender,
            Name(fUser.nameTitle, fUser.nameFirst, fUser.nameLast),
            fUser.email,
            Picture(fUser.pictureLarge, fUser.pictureMedium, fUser.pictureThumbnail),
            Login(fUser.loginUuid, fUser.loginUserName),
            fUser.phone,
            fUser.cell,
            Location(fUser.locationStreet, fUser.locationCity, fUser.locationState, fUser.locationPostCode),
            Id(fUser.idName, fUser.idValue),
            fUser.completedUserName
        )
    }
}
