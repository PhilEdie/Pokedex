package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: StatX
) : Serializable