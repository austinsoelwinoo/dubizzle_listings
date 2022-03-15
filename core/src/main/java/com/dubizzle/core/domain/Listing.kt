package com.dubizzle.core.domain

import com.dubizzle.core.common.prettifiedDate
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class Listing(
    open @SerializedName("created_at") var createdAt: String = "",
    open @SerializedName("price") var price: String = "",
    open  @SerializedName("name") var name: String = "",
    open @SerializedName("uid") var uid: String = "",
    open @SerializedName("image_ids") var imageIds: Array<String> = emptyArray(),
    open @SerializedName("image_urls") var imageUrls: Array<String> = emptyArray(),
    open @SerializedName("image_urls_thumbnails") var imageUrlsThumbnails: Array<String> = emptyArray()
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