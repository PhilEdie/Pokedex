package com.example.pokedex.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ability(
    @SerializedName("ability")
    val ability: AbilityX,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("slot")
    val slot: Int
) : Serializable