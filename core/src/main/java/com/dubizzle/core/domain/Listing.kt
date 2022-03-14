package com.dubizzle.core.domain

import com.dubizzle.core.common.prettifiedDate
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Listing(
    @SerializedName("created_at") var createdAt: String = "",
    @SerializedName("price") var price: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("uid") var uid: String = "",
    @SerializedName("image_ids") var imageIds: List<String> = listOf(),
    @SerializedName("image_urls") var imageUrls: List<String> = listOf(),
    @SerializedName("image_urls_thumbnails") var imageUrlsThumbnails: List<String> = listOf()
) {
    fun retrieveFirstImageUrl(): String {
        return imageUrls.firstOrNull() ?: ""
    }

    fun retrieveFirstImageThumbnail(): String {
        return imageUrlsThumbnails.firstOrNull() ?: ""
    }

    fun prettifiedCreatedAt(): String {
        return createdAt.prettifiedDate()
    }

    fun extractCreatedAtDateValue(): Date? {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH)
        return try {
            input.parse(createdAt)
        } catch (e: ParseException) {
            null
        }
    }

    fun extractPriceValue(): Int {
        return price.replace("AED ", "").toIntOrNull() ?: 0
    }
}