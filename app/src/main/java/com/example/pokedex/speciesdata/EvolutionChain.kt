package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EvolutionChain(
    @SerializedName("url")
    val url: String
) : Serializable