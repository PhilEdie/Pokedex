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

    /** The complete dataset containing all [Pokemon] objects.*/
    val dataset: HashMap<Int, Pokemon> = HashMap<Int, Pokemon>()

    /** Tracks which Pokemon should be displayed. The filter changes depending on what's
     * in the search bar.*/
    val datasetFiltered: HashMap<Int, Pokemon> = HashMap<Int, Pokemon>()

    //Setting up the OnClickListener:
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    /**
     * Provides a reference to the views for each Pokemon item.
     * Each item is just a [Pokemon] object.
     */
    class ItemViewHolder(private val view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val itemIdName: TextView = view.findViewById(R.id.item_id_name)
        val itemImage: ImageView = view.findViewById(R.id.item_image)

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

        //Get the Pokemon attached to the item.
        val item = datasetFiltered.toList()[position].second

        //assign the item text to be the Pokemon's ID and name.
        holder.itemIdName.text = ("#" + item.id.toString() + " " +
                item.name.replaceFirstChar { it.uppercase() })

        //Setting the background color of the item.
        val colorID = getColorIDByType(item?.types[0].type.name)
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, colorID))

        //Assign the artwork for the item.
        val resID = context.resources.getIdentifier(
            "p" + item?.id.toString(),
            "drawable",
            context.packageName
        )
        holder.itemImage.setImageResource(resID)

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = datasetFiltered.size

    /**
     * Loads new [Pokemon] objects into the dataset and the filter. Updates the UI
     * by calling notifyItemInserted.
     */
    fun addPokemon(pokemon: Pokemon) {
        datasetFiltered[pokemon.id] = pokemon
        dataset[pokemon.id] = pokemon
        notifyItemInserted(dataset.size - 1)
    }

    /**
     * Returns the ColorID of the type which matches the provided string.
     */
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

        //Type not found. return the default system color.
        return R.color.design_default_color_background
    }
}