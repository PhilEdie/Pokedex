package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int
) : Serializable