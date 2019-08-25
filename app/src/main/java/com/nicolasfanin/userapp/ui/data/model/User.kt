package com.nicolasfanin.userapp.ui.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("gender") val gender: String? = "no gender",
    @SerializedName("first") val first: String? = "no name",
    @SerializedName("last") val last: String? = "no name"
    )

data class Result(val results: List<User>)