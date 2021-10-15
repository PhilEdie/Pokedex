package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Move(
    @SerializedName("move")
    val move: MoveX,
) : Serializable