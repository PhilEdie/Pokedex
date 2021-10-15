package com.example.pokedex
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon
import com.example.pokedex.data.Species
import com.example.pokedex.speciesdata.SpeciesData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Initialize data.
        itemAdapter = ItemAdapter(this@MainActivity)

        recycler_view.layoutManager = LinearLayoutManager(this)
        itemAdapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@MainActivity, PokemonActivity::class.java)
                intent.putExtra("pokemon_extra", itemAdapter.datasetFiltered[position + 1])
                startActivity(intent)
            }

        })

        getPokemonData()
        recycler_view.adapter = itemAdapter
    }

    fun getPokemonData(){
        val client = OkHttpClient()
        for(id in 1..800) {
            createPokemon(client, id)
        }
    }

    fun createPokemon(client: OkHttpClient, id : Int){
        var pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + id
        var pokemonRequest = Request.Builder()
            .url(pokemonUrl)
            .build()

        client.newCall(pokemonRequest).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val gson = GsonBuilder().create()
                var pokemon = gson.fromJson(body, Pokemon::class.java)
                print("inside onResponse, toReturn = " + pokemon.name)
                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    itemAdapter.addPokemon(pokemon)
                    itemAdapter.datasetFiltered[pokemon.id] = pokemon
                    createSpecies(client, id)
                })

            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    fun createSpecies(client: OkHttpClient, id : Int){
        var speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + id
        var speciesRequest = Request.Builder()
            .url(speciesUrl)
            .build()

        client.newCall(speciesRequest).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                var species : SpeciesData = gson.fromJson(body, SpeciesData::class.java)
                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    itemAdapter.dataset[id]?.species?.speciesData  = species
                })

            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                itemAdapter.datasetFiltered.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    itemAdapter.dataset.forEach{
                        if(it.value.name.contains(searchText) || it.key.toString().contentEquals(searchText)){
                            itemAdapter.datasetFiltered.put(it.key, it.value)
                        }
                    }
                    itemAdapter!!.notifyDataSetChanged()
                } else {
                    itemAdapter.datasetFiltered.clear()
                    itemAdapter.datasetFiltered.putAll(itemAdapter.dataset)
                    itemAdapter!!.notifyDataSetChanged()
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }

}

