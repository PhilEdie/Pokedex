package com.example.pokedex

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon
import java.util.*
import kotlin.collections.HashMap


/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Pokemon] data object.
 */
class ItemAdapter(val context: Context, val mainActivity: MainActivity) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    /** The complete dataset containing all [Pokemon] objects.*/
    val dataset: TreeMap<Int, Pokemon> = TreeMap<Int, Pokemon>()

    /** Tracks which Pokemon should be displayed. The filter changes depending on what's
     * in the search bar.*/
    val datasetFiltered: TreeMap<Int, Pokemon> = TreeMap<Int, Pokemon>()

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
        holder.itemView
        //Get the Pokemon attached to the item.
        val item = datasetFiltered.toList()[position].second

        //assign the item text to be the Pokemon's ID and name.
        holder.itemIdName.text = ("#" + item.id.toString() + " " +
                item.name.replaceFirstChar { it.uppercase() })

        //Setting the background color of the item.
        var colorID = ColorUtil.getColorIDByTypeString(item?.types[0].type.name)
        colorID = ColorUtil.lightenColor(ContextCompat.getColor(context, colorID), 0.2f)
        holder.itemView.setBackgroundColor(colorID)

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
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position


    /**
     * Loads new [Pokemon] objects into the dataset and the filter. Updates the UI
     * by calling notifyItemInserted.
     */
    fun addPokemon(pokemon: Pokemon) {

        dataset[pokemon.id] = pokemon
        if(!mainActivity.isSearching()) {
            datasetFiltered[pokemon.id] = pokemon
        }
        notifyItemInserted(dataset.size - 1)
    }

}