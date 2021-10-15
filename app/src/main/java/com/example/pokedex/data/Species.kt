package com.example.pokedex.data


import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.pokedex.MainActivity
import com.example.pokedex.speciesdata.SpeciesData
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.*
import java.io.IOException
import java.io.Serializable

data class Species(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("speciesData")
    var speciesData : SpeciesData
) : Serializable




