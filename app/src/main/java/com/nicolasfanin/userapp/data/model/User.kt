package com.nicolasfanin.userapp.data.model

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
    var completeUserName: String? = "no complete name"
)

data class Name(
    @SerializedName("title") val title: String? = "no title name",
    @SerializedName("first") val first: String? = "no first name",
    @SerializedName("last") val last: String? = "no last name"
)

data class Picture(
    @SerializedName("large") val large: String? = "no large image",
    @SerializedName("medium") val medium: String? = "no medium image",
    @SerializedName("thumbnail") val thumbnail: String? = "no thumbnail image"
)

data class Login(
    @SerializedName("username") val username: String? = "no username"
)

data class Location(
    @SerializedName("street") val street: String? = "no street",
    @SerializedName("city") val city: String? = "no city",
    @SerializedName("state") val state: String? = "no state",
    @SerializedName("postcode") val postcode: String? = "no postcode"
)

data class Result(val results: List<User>)