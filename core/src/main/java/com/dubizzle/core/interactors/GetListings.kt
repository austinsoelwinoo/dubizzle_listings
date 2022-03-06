package com.dubizzle.core.interactors

import com.dubizzle.core.data.ListingsRemoteRepository

class GetListings(private val repository: ListingsRemoteRepository) {
  suspend operator fun invoke() = repository.getDocuments()
}
