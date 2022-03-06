package com.dubizzle.listings.framework.api


import retrofit2.Response
import retrofit2.http.GET

interface ListingsApi {
    @GET("dynamodb-writer/")
    suspend fun getBooks(): Response<ListingApiResponse>
}