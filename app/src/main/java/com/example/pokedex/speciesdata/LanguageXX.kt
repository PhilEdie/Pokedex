package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName

data class LanguageXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)