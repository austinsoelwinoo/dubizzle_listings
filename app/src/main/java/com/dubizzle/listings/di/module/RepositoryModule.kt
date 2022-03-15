package com.dubizzle.listings.di.module

import com.dubizzle.listings.core.data.ListingsRemoteRepository
import com.dubizzle.listings.core.interactors.GetListings
import com.dubizzle.listings.framework.Interactors
import org.koin.dsl.module

val repoModule = module {
    single {
        ListingsRemoteRepository(get())
    }

    single {
        Interactors(get())
    }

    single {
        GetListings(get())
    }
}