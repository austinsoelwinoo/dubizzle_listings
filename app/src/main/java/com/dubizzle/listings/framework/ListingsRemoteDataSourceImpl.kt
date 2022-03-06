package com.dubizzle.listings.framework

import com.dubizzle.core.data.ListingsRemoteDataSource
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.framework.api.ListingsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListingsRemoteDataSourceImpl(
    private val service: ListingsApi
) : ListingsRemoteDataSource {
    override suspend fun readAll(): List<Listing> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getBooks()
                if (response.isSuccessful) {
                    return@withContext response.body()?.results ?: emptyList()
                } else {
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList()
            }
        }
}