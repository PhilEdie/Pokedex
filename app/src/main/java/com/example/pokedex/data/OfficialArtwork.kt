package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
) : Serializable
