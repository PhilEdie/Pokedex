package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.model.Stat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataList : MutableList<String> = mutableListOf()
    private lateinit var pokemonAdapter: PokemonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPokemonItems.adapter = pokemonAdapter
        rvPokemonItems.layoutManager = LinearLayoutManager(this)

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/ditto")
            .build()
            .getAsObject(PokemonModel::class.java, object : ParsedRequestListener<PokemonModel>{
                override fun onResponse(response: PokemonModel) {
                    val name = response.name
                    val type = response.types
                    val image = response.sprites.frontDefault
                    dataList.add(PokemonModel(name : Name,type,image))
                    pokemonAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    TODO("Not yet implemented")
                }

            })

        pokemonAdapter = PokemonAdapter(mutableListOf())



    }
}

