package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String,
    @SerializedName("language")
    val language: Language,
) : Serializable