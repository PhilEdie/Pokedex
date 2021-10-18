package com.example.pokedex

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.Pokemon
import com.example.pokedex.speciesdata.SpeciesData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {


    private lateinit var itemAdapter: ItemAdapter
    private lateinit var searchView: SearchView
    private var searching: Boolean =
        false     //Prevents items from being added to the filter when the user is searching.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data.
        itemAdapter = ItemAdapter(this@MainActivity, this)

        recycler_view.layoutManager = LinearLayoutManager(this)

        itemAdapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@MainActivity, PokemonActivity::class.java)
                intent.putExtra(
                    "pokemon_extra",
                    itemAdapter.datasetFiltered.toList()[position].second
                )

                //Hiding the keyboard when entering next activity.
                val imm: InputMethodManager =
                    this@MainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val view = this@MainActivity.currentFocus
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }

                //Open the selected Pokemon's summary page.
                startActivity(intent)

                //Deselect the search bar.
                searchView.clearFocus()
            }
        })

        getPokemonData()
        recycler_view.adapter = itemAdapter
    }


    /**
     * Loops through all 898 Pokemon id's, calls [createPokemon] to create [Pokemon] objects.
     */
    private fun getPokemonData() {
        val client = OkHttpClient()
        for (id in 1..898) {
            createPokemon(client, id)
        }
    }

    fun isSearching(): Boolean {
        return searching
    }

    /**
     * Parses [Pokemon] object from PokeAPI. Adds the [Pokemon] object to the dataset.
     */
    private fun createPokemon(client: OkHttpClient, id: Int) {
        val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/$id"
        val pokemonRequest = Request.Builder()
            .url(pokemonUrl)
            .build()

        client.newCall(pokemonRequest).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val pokemon = gson.fromJson(body, Pokemon::class.java)
                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    itemAdapter.addPokemon(pokemon)
                    createSpecies(client, id)
                })
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    /**
     * Parses [SpeciesData] object from PokeAPI. adds [SpeciesData] to [Pokemon] object with the given id.
     */
    fun createSpecies(client: OkHttpClient, id: Int) {
        thread(start = true) {
            val speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/$id"
            val speciesRequest = Request.Builder()
                .url(speciesUrl)
                .build()

            client.newCall(speciesRequest).execute().use { response ->
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val species: SpeciesData = gson.fromJson(body, SpeciesData::class.java)
                itemAdapter.dataset[id]?.species?.speciesData = species
            }
        }
    }

    /**
     * Adds a search bar to the options menu.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)

        //Initialise searchView field.
        searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Reset the filter.
                itemAdapter.datasetFiltered.clear()
                //Make the search case insensitive.
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    searching =
                        true    //Currently searching, stop adding loaded items to the filter.
                    //Select only Pokemon who's names start with the search string or who's id's match the search string.
                    itemAdapter.dataset.forEach {
                        //Initial
                        if (it.value.name.startsWith(searchText) || it.key.toString()
                                .contentEquals(searchText)
                        ) {
                            itemAdapter.datasetFiltered[it.key] = it.value
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                } else {

                    //Add all Pokemon to the filter.
                    itemAdapter.datasetFiltered.putAll(itemAdapter.dataset)
                    itemAdapter.notifyDataSetChanged()
                    searching = false   //Finished searching. safe to add loaded items to filter.
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

}

