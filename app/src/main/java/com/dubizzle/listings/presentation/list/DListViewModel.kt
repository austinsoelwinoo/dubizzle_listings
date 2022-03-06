package com.dubizzle.listings.presentation.list

import androidx.lifecycle.MutableLiveData
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.framework.DBaseViewModel
import com.dubizzle.listings.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DListViewModel (interactors: Interactors)
    : DBaseViewModel(interactors) {

    val listings: MutableLiveData<List<Listing>> = MutableLiveData()

    fun loadDocuments() {
        GlobalScope.launch {
            listings.postValue(interactors.getListings())
        }
    }
}