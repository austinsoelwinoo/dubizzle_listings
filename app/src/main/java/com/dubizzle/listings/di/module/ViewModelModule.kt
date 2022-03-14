package com.dubizzle.listings.di.module

import com.dubizzle.listings.presentation.list.DListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { params -> DListViewModel(get(), params.get()) }
}