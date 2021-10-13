package com.example.pokedex
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data.
        itemAdapter = ItemAdapter(mutableListOf<Pokemon>())
        //val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true)

        recycler_view.adapter = itemAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        val client = OkHttpClient()

        for(i in 1..152) {

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
                    })

                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
            })
        }
    }
}

