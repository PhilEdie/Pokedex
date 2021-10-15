package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: LanguageXX,
    @SerializedName("name")
    val name: String
)