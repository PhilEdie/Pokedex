package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genera(
    @SerializedName("genus")
    val genus: String,
    @SerializedName("language")
    val language: LanguageX
) : Serializable