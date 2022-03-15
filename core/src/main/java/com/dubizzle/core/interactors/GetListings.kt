package com.dubizzle.core.interactors

import com.dubizzle.core.data.ListingsRemoteRepository
import com.dubizzle.core.domain.Listing

class GetListings<T : Listing>(private val repository: ListingsRemoteRepository<T>) {
    suspend operator fun invoke() = repository.getDocuments()
}
