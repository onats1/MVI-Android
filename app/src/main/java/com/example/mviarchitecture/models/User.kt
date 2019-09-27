package com.example.mviarchitecture.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("email")
    var email: String? = null,

    @Expose
    @SerializedName("username")
    var username: String? = null,

    @Expose
    @SerializedName("image")
    var image: String? = null
){

    override fun toString(): String {
        return "User(email=$email, username=$username, image=$image)"
    }
}