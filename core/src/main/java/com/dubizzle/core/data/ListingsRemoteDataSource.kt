package com.dubizzle.core.data

import com.dubizzle.core.domain.Listing

interface ListingsRemoteDataSource {
  suspend fun readAll(): List<Listing>
}