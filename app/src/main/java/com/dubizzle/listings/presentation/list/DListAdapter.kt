package com.dubizzle.listings.presentation.list

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.R
import com.dubizzle.listings.presentation.inflate
import kotlin.properties.Delegates

class DListAdapter : RecyclerView.Adapter<DListAdapter.ViewHolder>() {

    var items: List<Listing> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.view_list_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(listing: Listing) {
            itemView.findViewById<TextView>(R.id.tvItemName).text = listing.name
        }
    }
}
