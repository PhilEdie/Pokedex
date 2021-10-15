package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypeX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Serializable