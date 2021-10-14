package com.example.pokedex

import android.content.ContentValues.TAG
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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon.*
import org.w3c.dom.Text

class PokemonActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val item_id : TextView = findViewById(R.id.pokemon_id)
        val item_name : TextView = findViewById(R.id.pokemon_name)
        val item_type1 : TextView = findViewById(R.id.pokemon_type1)
        val item_type2 : TextView = findViewById(R.id.pokemon_type2)


        val bundle : Bundle?= intent.extras
        val id = bundle!!.getInt("id")
        val name = bundle!!.getString("name")
        val type1 = bundle!!.getString("type1")
        var type2 : String = ""
        if(bundle.getString("type2").isNullOrEmpty()){
            item_type2.visibility = View.GONE
        } else {
            type2 = bundle!!.getString("type2").toString()

        }


        item_id.text = "#" + id.toString()
        item_name.text = name.toString().replaceFirstChar { it.uppercase() }
        item_type1.text = type1
        item_type2.text = type2

        if (type1 != null) {
            setTypeBackgroundColor(item_type1, type1)
        }

        if (type2 != null) {
            setTypeBackgroundColor(item_type2, type2)
        }


        loadImage(id)

        var stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)

        var barWidth : Float = 9f
        var spacing : Float = 10f

        var hp = bundle!!.getInt("hp").toFloat()
        var atk = bundle!!.getInt("atk").toFloat()
        var def = bundle!!.getInt("def").toFloat()
        var spatk = bundle!!.getInt("spatk").toFloat()
        var spdef = bundle!!.getInt("spdef").toFloat()
        var speed = bundle!!.getInt("speed").toFloat()

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

    private fun loadImage(id : Int){
        val item_image : ImageView = findViewById(R.id.pokemon_image)
        var imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + id + ".png"
        if(id.toString().length == 1){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00" + id + ".png"
        } else if(id.toString().length == 2){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0" + id + ".png"
        }

        Picasso.get().load(imageUri).placeholder(R.drawable.pokeball).into(item_image)

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

        val xAxisLabels = listOf(".", "Speed", "Sp.Def", "Sp.Atk", "Defense", "Attack", "HP")
        stats.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)


        stats.animateY(500)
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