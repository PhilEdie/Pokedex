package com.example.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R.layout
import com.example.pokedex.model.PokemonModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonAdapter (
    private val pokemons: MutableList<PokemonModel>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layout.item_pokemon,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val curPokemon = pokemons[position]
        holder.itemView.apply {
            tvPokemonName.text = curPokemon.name
            Picasso.get().load(curPokemon.sprites.frontDefault).into(imPokemonImage)
        }
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }
}