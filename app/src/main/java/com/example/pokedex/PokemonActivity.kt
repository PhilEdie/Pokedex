package com.example.pokedex

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
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


        //Select a random English flavortext.
        var flavorTexts = pokemon.species.speciesData.flavorTextEntries
        flavorTexts = flavorTexts.shuffled()
        for (i in flavorTexts.indices) {
            if(flavorTexts[i].language.name == "en"){
                flavorText.text = flavorTexts[i].flavorText.replace("\n", " ")
                break
            }
        }

        //If the Pokemon has only one type, hide the text-box for the second type.
        if(pokemon.types.size == 1){
            itemType2.visibility = View.GONE
        } else {
            itemType2.text = pokemon.types[1].type.name
        }

        //Set the type's text-box color to the type's color.
        val type1Drawable : Drawable = itemType1.background
        val type1ColorID = ColorUtil.getColorIDByTypeString(pokemon.types[0].type.name)
        type1Drawable.mutate().setColorFilter(ContextCompat.getColor(this@PokemonActivity, type1ColorID), PorterDuff.Mode.SRC_IN)

        if (pokemon.types.size == 2) {
            val type2Drawable : Drawable = itemType2.background
            val type2ColorID = ColorUtil.getColorIDByTypeString(pokemon.types[1].type.name)
            type2Drawable.mutate().setColorFilter(ContextCompat.getColor(this@PokemonActivity, type2ColorID), PorterDuff.Mode.SRC_IN)
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
        barDataSet.setColors(
            ContextCompat.getColor(this@PokemonActivity, R.color.speed),
            ContextCompat.getColor(this@PokemonActivity, R.color.spdef),
            ContextCompat.getColor(this@PokemonActivity, R.color.spatk),
            ContextCompat.getColor(this@PokemonActivity, R.color.def),
            ContextCompat.getColor(this@PokemonActivity, R.color.atk),
            ContextCompat.getColor(this@PokemonActivity, R.color.hp)
            )
        barDataSet.valueTextSize = 13f

        val data = BarData(barDataSet)
        data.setValueFormatter(object : ValueFormatter() {

            override fun getFormattedValue(value: Float): String? {
                return "" + value.toInt()
            }
        })
        return data
    }

}