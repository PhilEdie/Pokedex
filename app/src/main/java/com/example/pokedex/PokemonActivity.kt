package com.example.pokedex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokedex.data.Pokemon
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_pokemon.*


class PokemonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        //Get bundled Pokemon data and assign data to the Activity's fields.
        val bundle : Bundle?= intent.extras
        val pokemon : Pokemon = bundle!!.get("pokemon_extra") as Pokemon
        initialiseTextAndImages(pokemon)

        //Create the stats chart:
        val stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)
        stats.data = initialiseStatsData(pokemon)
        setStatChartParameters()
    }

    private fun initialiseTextAndImages(pokemon : Pokemon){

        //Load the Pokemon artwork from drawable directory.
        val resID = resources.getIdentifier("p" + pokemon.id.toString(), "drawable", packageName)
        pokemon_image.setImageResource(resID)

        //Get fields to place data into.
        val itemIdName : TextView = findViewById(R.id.pokemon_id_name)
        val itemType1 : TextView = findViewById(R.id.pokemon_type1)
        val itemType2 : TextView = findViewById(R.id.pokemon_type2)
        val species : TextView = findViewById(R.id.pokemon_species)
        val flavorText : TextView = findViewById(R.id.pokemon_flavortext)


        //Place data into the fields.

        //Displays id and name in format "#1 Bulbasaur".
        itemIdName.text = ("#" + pokemon.id.toString() + " " + pokemon.name.replaceFirstChar { it.uppercase() })
        itemType1.text = pokemon.types[0].type.name
        species.text = pokemon.species.speciesData.genera[7].genus
        flavorText.text = pokemon.species.speciesData.flavorTextEntries[0].flavorText.replace("\n", " ")


        //If the Pokemon has only one type, hide the text-box for the second type.
        if(pokemon.types.size == 1){
            itemType2.visibility = View.GONE
        } else {
            itemType2.text = pokemon.types[1].type.name
        }


        //Set the type's text-box color to the type's color.
        setTypeBackgroundColor(itemType1, pokemon.types[0].type.name)
        if (pokemon.types.size == 2) {
            setTypeBackgroundColor(itemType2, pokemon.types[1].type.name)
        }

    }




    /** Assigns required parameters to the Pokemon's stats chart. */
    private fun setStatChartParameters(){
        val stats : HorizontalBarChart = findViewById(R.id.pokemon_stats)
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

    /** Converts all individual stats into data which can be used by [HorizontalBarChart].*/
    private fun initialiseStatsData(pokemon : Pokemon) : BarData{
        val hp = pokemon.stats[5].baseStat.toFloat()
        val atk = pokemon.stats[4].baseStat.toFloat()
        val def = pokemon.stats[3].baseStat.toFloat()
        val spatk = pokemon.stats[2].baseStat.toFloat()
        val spdef = pokemon.stats[1].baseStat.toFloat()
        val speed = pokemon.stats[0].baseStat.toFloat()

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
        return data
    }


    /** Gets a color from colors.xml which matches typeString. Assigns the color to provided type [TextView].*/
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