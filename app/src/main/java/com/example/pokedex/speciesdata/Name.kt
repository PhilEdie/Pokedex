package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Name(
    @SerializedName("language")
    val language: LanguageXX,
    @SerializedName("name")
    val name: String
) : Serializable