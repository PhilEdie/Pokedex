package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
) : Serializable