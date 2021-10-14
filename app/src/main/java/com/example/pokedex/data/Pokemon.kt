package com.example.pokedex.data

data class Pokemon(val id: Int,
val name: String,
val stats: Stats)

data class Stats(
    val base_stat: Int,
    val stat: Stat
)

data class Stat(
    val name: String
)