package com.dubizzle.core.data

import com.dubizzle.core.domain.Listing

interface ListingsRemoteDataSource<T : Listing>{
    suspend fun readAll(): List<T>
}