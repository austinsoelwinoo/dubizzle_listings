package com.dubizzle.listings.core.interactors

import com.dubizzle.listings.core.data.ListingsRemoteRepository

class GetListings(private val repository: ListingsRemoteRepository) {
    suspend operator fun invoke() = repository.getDocuments()
}
