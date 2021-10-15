package com.example.pokedex.speciesdata


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Variety(
    @SerializedName("is_default")
    val isDefault: Boolean,
) : Serializable