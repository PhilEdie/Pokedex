package com.example.pokedex
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon
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
        itemAdapter = ItemAdapter(mutableListOf<Pokemon>())

        recycler_view.layoutManager = LinearLayoutManager(this)
        itemAdapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity, "You clicked on $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, PokemonActivity::class.java)
                intent.putExtra("name", itemAdapter.dataset[position].name)
                intent.putExtra("id", itemAdapter.dataset[position].id)
                intent.putExtra("hp", itemAdapter.dataset[position].stats[5].baseStat)
                intent.putExtra("atk", itemAdapter.dataset[position].stats[4].baseStat)
                intent.putExtra("def", itemAdapter.dataset[position].stats[3].baseStat)
                intent.putExtra("spatk", itemAdapter.dataset[position].stats[2].baseStat)
                intent.putExtra("spdef", itemAdapter.dataset[position].stats[1].baseStat)
                intent.putExtra("speed", itemAdapter.dataset[position].stats[0].baseStat)
                startActivity(intent)
            }

        })

        getPokemonData()
        recycler_view.adapter = itemAdapter

    }

    fun getPokemonData(){
        val client = OkHttpClient()
        for(i in 1..800) {

            var url = "https://pokeapi.co/api/v2/pokemon/" + i
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {

                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    var pokemon = gson.fromJson(body, Pokemon::class.java)
                    print("inside onResponse, toReturn = " + pokemon.name)
                    this@MainActivity.runOnUiThread(java.lang.Runnable {
                        itemAdapter.addPokemon(pokemon)
                        itemAdapter.datasetFiltered.add(pokemon)
                    })

                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                itemAdapter.datasetFiltered.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    itemAdapter.dataset.forEach{
                        if(it.name.contains(searchText) || it.id.toString().contains(searchText)){
                            itemAdapter.datasetFiltered.add(it)
                            Toast.makeText(this@MainActivity, "added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                } else {
                    itemAdapter.datasetFiltered.clear()
                    itemAdapter.datasetFiltered.addAll(itemAdapter.dataset)
                    itemAdapter.notifyDataSetChanged()
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}

