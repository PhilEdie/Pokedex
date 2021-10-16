package com.example.pokedex

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon


/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Pokemon] data object.
 */
class ItemAdapter(val context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    val dataset: HashMap<Int, Pokemon> = HashMap<Int, Pokemon>()
    val datasetFiltered: HashMap<Int, Pokemon> = HashMap<Int, Pokemon>()

    //Setting up the OnClickListener:
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an com.example.pokedex.data.Affirmation object.
    class ItemViewHolder(private val view: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val item_id_name: TextView = view.findViewById(R.id.item_id_name)
        val item_image: ImageView = view.findViewById(R.id.item_image)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout, mListener)

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = datasetFiltered.toList()[position].second  //May need to add 1.

        holder.item_id_name.text =
            "#" + item?.id.toString() + " " + item?.name?.replaceFirstChar { it.uppercase() }

        //Setting the background color
        var colorID = getColorIDByType(item?.types[0].type.name)
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, colorID))

        var resID = context.resources.getIdentifier(
            "p" + item?.id.toString(),
            "drawable",
            context.packageName
        )
        holder.item_image.setImageResource(resID)

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = datasetFiltered.size

    fun addPokemon(pokemon: Pokemon) {
        datasetFiltered[pokemon.id] = pokemon
        dataset[pokemon.id] = pokemon
        notifyItemInserted(dataset.size - 1)
    }

    private fun getColorIDByType(typeString: String): Int {
        when (typeString) {
            "normal" -> return R.color.normal
            "fire" -> return R.color.fire
            "water" -> return R.color.water
            "electric" -> return R.color.electric
            "grass" -> return R.color.grass
            "ice" -> return R.color.ice
            "fighting" -> return R.color.fighting
            "poison" -> return R.color.poison
            "ground" -> return R.color.ground
            "flying" -> return R.color.flying
            "psychic" -> return R.color.psychic
            "bug" -> return R.color.bug
            "rock" -> return R.color.rock
            "ghost" -> return R.color.ghost
            "dragon" -> return R.color.dragon
            "dark" -> return R.color.dark
            "steel" -> return R.color.steel
            "fairy" -> return R.color.fairy
        }
        return R.color.design_default_color_background
    }
}