package com.dubizzle.core.domain

import com.google.gson.annotations.SerializedName

data class Listing(
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("price") var price: String,
    @SerializedName("name") var name: String,
    @SerializedName("uid") var uid: String,
    @SerializedName("image_ids") var imageIds: List<String> = listOf(),
    @SerializedName("image_urls") var imageUrls: List<String> = listOf(),
    @SerializedName("image_urls_thumbnails") var imageUrlsThumbnails: List<String> = listOf()
)