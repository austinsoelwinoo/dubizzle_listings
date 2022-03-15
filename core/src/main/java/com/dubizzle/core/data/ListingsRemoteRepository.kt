package com.dubizzle.core.data

import com.dubizzle.core.domain.Listing

class ListingsRemoteRepository<T : Listing>(private val datasource: ListingsRemoteDataSource<T>) {
    suspend fun getDocuments() = datasource.readAll()
}