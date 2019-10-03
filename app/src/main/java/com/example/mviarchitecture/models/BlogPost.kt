package com.example.mviarchitecture.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogPost(

    @Expose
    @SerializedName("pk")
    var pk: Int? = null,

    @Expose
    @SerializedName("title")
    var title: String? = null,

    @Expose
    @SerializedName("body")
    var body: String? = null,

    @Expose
    @SerializedName("image")
    var image: String? = null
){

    override fun equals(other: Any?): Boolean {

        if(javaClass != other?.javaClass){
            return false
        }

        other as BlogPost

        if (pk != other.pk){
            return false
        }

        return true
    }

    override fun toString(): String {
        return "BlogPost(pk=$pk, title=$title, body=$body, image=$image)"
    }

    override fun hashCode(): Int {
        var result = pk ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }
}