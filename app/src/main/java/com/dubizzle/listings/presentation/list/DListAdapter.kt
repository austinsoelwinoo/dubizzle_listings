package com.dubizzle.listings.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.databinding.ViewListItemBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class DListAdapter : RecyclerView.Adapter<DListAdapter.ViewHolder>() {

    var items: List<Listing> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ViewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listing: Listing) {
            Glide
                .with(this.itemView)
                .load(listing.imageUrlsThumbnails[0])
                .into(binding.ivListing)
        }
    }
}
