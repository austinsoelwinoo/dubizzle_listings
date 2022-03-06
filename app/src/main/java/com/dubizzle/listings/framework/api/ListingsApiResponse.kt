package com.dubizzle.listings.framework.api
import com.dubizzle.core.domain.Listing
import com.google.gson.annotations.SerializedName

data class ListingApiResponse (
    @SerializedName("results"    ) var results    : List<Listing> = listOf(),
    @SerializedName("pagination" ) var pagination : Pagination?        = Pagination()
)
data class Pagination (
    @SerializedName("key" ) var key : String? = null
)