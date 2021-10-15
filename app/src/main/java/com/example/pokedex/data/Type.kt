package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeX
) : Serializable