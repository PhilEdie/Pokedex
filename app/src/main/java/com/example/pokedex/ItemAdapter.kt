package com.example.pokedex
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.Pokemon
import com.github.mikephil.charting.data.BarEntry
import com.squareup.picasso.Picasso


/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class ItemAdapter(
    val dataset: MutableList<Pokemon>

) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    val datasetFiltered: ArrayList<Pokemon> = ArrayList()


    //Setting up the OnClickListener:
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position:Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an com.example.pokedex.data.Affirmation object.
    class ItemViewHolder(private val view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val item_id: TextView = view.findViewById(R.id.item_id)
        val item_name: TextView = view.findViewById(R.id.item_name)
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
        val item = datasetFiltered[position]
        holder.item_id.text = "#" + item.id.toString()
        holder.item_name.text = item.name.replaceFirstChar { it.uppercase() }
        var imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + item.id + ".png"
        if(item.id.toString().length == 1){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00" + item.id + ".png"
        } else if(item.id.toString().length == 2){
            imageUri = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0" + item.id + ".png"
        }

        Picasso.get().load(imageUri).placeholder(R.drawable.pokeball).into(holder.item_image)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = datasetFiltered.size

    fun addPokemon(pokemon: Pokemon){
        dataset.add(pokemon)
        notifyItemInserted(dataset.size-1)
    }
}