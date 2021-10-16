package com.example.pokedex

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import com.example.pokedex.data.Pokemon
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon.*
import org.w3c.dom.Text
import com.github.mikephil.charting.utils.ViewPortHandler




class PokemonActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val bundle : Bundle?= intent.extras
        var bundle_value : Pokemon = bundle!!.get("pokemon_extra") as Pokemon

        var imageUri = bundle_value?.sprites?.other?.officialArtwork?.frontDefault

        var resID = resources.getIdentifier("p" + bundle_value?.id.toString(), "drawable", packageName)
        pokemon_image.setImageResource(resID)

        val item_id_name : TextView = findViewById(R.id.pokemon_id_name)
        val item_type1 : TextView = findViewById(R.id.pokemon_type1)
        val item_type2 : TextView = findViewById(R.id.pokemon_type2)
        val species : TextView = findViewById(R.id.pokemon_species)
        val flavor_text : TextView = findViewById(R.id.pokemon_flavortext)

        item_id_name.text = "#" + bundle_value.id.toString() + " " + bundle_value.name.replaceFirstChar { it.uppercase() }
        item_type1.text = bundle_value.types[0].type.name
        if(bundle_value.species.speciesData != null){
            species.text = bundle_value.species.speciesData.genera[7].genus
            flavor_text.text = bundle_value.species.speciesData.flavorTextEntries[0].flavorText.replace("\n", " ")
        }


        if(bundle_value.types.size == 1){
            item_type2.visibility = View.GONE
        } else {
            item_type2.text = bundle_value.types[1].type.name
        }

        setTypeBackgroundColor(item_type1, bundle_value.types[0].type.name)


        if (bundle_value.types.size == 2) {
            setTypeBackgroundColor(item_type2, bundle_value.types[1].type.name)
        }

        var stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)



        var hp = bundle_value.stats[5].baseStat.toFloat()
        var atk = bundle_value.stats[4].baseStat.toFloat()
        var def = bundle_value.stats[3].baseStat.toFloat()
        var spatk = bundle_value.stats[2].baseStat.toFloat()
        var spdef = bundle_value.stats[1].baseStat.toFloat()
        var speed = bundle_value.stats[0].baseStat.toFloat()

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, hp))
        entries.add(BarEntry(2f, atk))
        entries.add(BarEntry(3f, def))
        entries.add(BarEntry(4f, spatk))
        entries.add(BarEntry(5f, spdef))
        entries.add(BarEntry(6f, speed))

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        barDataSet.valueTextSize = 13f

        val data = BarData(barDataSet)
        data.setValueFormatter(object : ValueFormatter() {

            override fun getFormattedValue(value: Float): String? {
                return "" + value.toInt()
            }

        })

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
        stats.axisLeft.axisMaximum = 255f
        stats.axisLeft.axisMinimum = 0f
        stats.axisLeft.isEnabled = false
        stats.setTouchEnabled(false)
        stats.isDragEnabled = false
        stats.setScaleEnabled(false)
        stats.setPinchZoom(false)
        stats.xAxis.textSize = 13f
        stats.animateY(500)
        stats.xAxis.position = XAxis.XAxisPosition.BOTTOM
        val xAxisLabels = listOf(".", "Speed", "Sp.Def", "Sp.Atk", "Defense", "Attack", "HP")
        stats.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        stats.invalidate()
    }

    private fun setTypeBackgroundColor(type : TextView, typeString : String){
        when(typeString) {
            "normal" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.normal))
            "fire" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.fire))
            "water" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.water))
            "electric" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.electric))
            "grass" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.grass))
            "ice" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.ice))
            "fighting" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.fighting))
            "poison" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.poison))
            "ground" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.ground))
            "flying" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.flying))
            "psychic" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.psychic))
            "bug" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.bug))
            "rock" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.rock))
            "ghost" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.ghost))
            "dragon" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.dragon))
            "dark" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.dark))
            "steel" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.steel))
            "fairy" -> type.setBackgroundColor(ContextCompat.getColor(this,R.color.fairy))
        }
    }



}