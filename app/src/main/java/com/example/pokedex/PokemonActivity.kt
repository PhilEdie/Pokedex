package com.example.pokedex

import android.content.ContentValues.TAG
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.example.pokedex.data.Pokemon
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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

        var stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)

        var barWidth : Float = 9f
        var spacing : Float = 10f

        var hp : Float = 100f
        var atk : Float = 80f
        var def : Float = 120f
        var spatk : Float = 30f
        var spdef : Float = 60f
        var speed : Float = 30f

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, hp))
        entries.add(BarEntry(2f, atk))
        entries.add(BarEntry(3f, def))
        entries.add(BarEntry(4f, spatk))
        entries.add(BarEntry(5f, spdef))
        entries.add(BarEntry(6f, speed))

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)

        val data = BarData(barDataSet)

        stats.data = data
        initialiseStatsChart()
    }

    private fun initialiseStatsChart(){
        var stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)
        stats.axisLeft.setDrawGridLines(false)
        stats.xAxis.setDrawGridLines(false)
        stats.xAxis.setDrawAxisLine(false)

        stats.axisRight.isEnabled = false
        stats.legend.isEnabled = false

        stats.description.isEnabled = false
        stats.animateY(500)
        stats.invalidate()
    }

}