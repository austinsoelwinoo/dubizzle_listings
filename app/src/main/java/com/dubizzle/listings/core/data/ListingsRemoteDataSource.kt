package com.dubizzle.listings.core.data

import com.dubizzle.listings.core.domain.Listing

interface ListingsRemoteDataSource {
    suspend fun readAll(): List<Listing>
}