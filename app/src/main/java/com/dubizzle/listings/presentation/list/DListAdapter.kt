package com.dubizzle.listings.presentation.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.databinding.ViewListItemBinding
import com.dubizzle.minimalimageloader.ImageLoader
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class DListAdapter(
    private val context: Context,
    private val listener: ActionClickListener
) : RecyclerView.Adapter<DListAdapter.ViewHolder>() {

    var items: List<Listing> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = items[position]
        holder.bind(listing)
        holder.binding.ivListing.setOnClickListener {
            listener.clicked(listing)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ViewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listing: Listing) {
            ImageLoader.with(binding.root.context)
                .load(binding.ivListing, listing.retrieveFirstImageThumbnail())
//            Glide
//                .with(this.itemView)
//                .load(listing.retrieveFirstImageThumbnail())
//                .into(binding.ivListing)
        }
    }

    interface ActionClickListener {
        fun clicked(listing: Listing)
    }
}
