package es.jccarrillo.simplelistkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.databinding.ItemSingleitemBinding

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items = ArrayList<Item>()
    var events: AdapterEvents? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSingleitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setItems(other: ArrayList<Item>) {
        items.clear()
        items.addAll(other)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position == itemCount - 1)
            events?.onLastItemShowed()
    }

    override fun getItemCount(): Int = items.size

    private fun getItem(position: Int): Item = items[position]

    inner class ViewHolder(private val itemBinding: ItemSingleitemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var currentItem: Item

        init {
            itemBinding.root.setOnClickListener {
                currentItem.let {
                    events?.onItemClicked(it)
                }
            }
        }

        fun bind(item: Item) {
            currentItem = item

            itemBinding.textViewName.text = item.name
            itemBinding.textViewDescription.text = item.description
            itemBinding.textViewOwner.text = item.owner.login

            Picasso.get().load(item.owner.avatarUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_help)
                .fit()
                .into(itemBinding.imageViewAvatar)
        }
    }

    interface AdapterEvents {
        fun onLastItemShowed()
        fun onItemClicked(item: Item)
    }
}