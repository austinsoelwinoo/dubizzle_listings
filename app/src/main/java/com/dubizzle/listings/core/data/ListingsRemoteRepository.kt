package com.dubizzle.listings.core.data


class ListingsRemoteRepository(private val datasource: ListingsRemoteDataSource) {
    suspend fun getDocuments() = datasource.readAll()
}