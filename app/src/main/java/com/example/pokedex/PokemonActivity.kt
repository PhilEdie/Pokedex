package com.example.pokedex

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon.*

class PokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val item_id : TextView = findViewById(R.id.pokemon_id)
        val item_name : TextView = findViewById(R.id.pokemon_name)
        val item_image : ImageView = findViewById(R.id.pokemon_image)

        val bundle : Bundle?= intent.extras
        val id = bundle!!.getInt("id")
        val name = bundle!!.getString("name")

        item_id.text = "#" + id.toString()
        item_name.text = name.toString().replaceFirstChar { it.uppercase() }

        var imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + id + ".png"
        if(id.toString().length == 1){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00" + id + ".png"
        } else if(id.toString().length == 2){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0" + id + ".png"
        }

        Picasso.get().load(imageUri).placeholder(R.drawable.pokeball).into(item_image)

    }
}