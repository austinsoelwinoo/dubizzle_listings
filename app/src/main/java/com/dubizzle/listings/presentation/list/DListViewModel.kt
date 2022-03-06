package com.dubizzle.listings.presentation.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.framework.DBaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DListViewModel (application: Application, interactors: Interactors)
    : DBaseViewModel(application, interactors) {

    val documents: MutableLiveData<List<Listing>> = MutableLiveData()

    fun loadDocuments() {
        GlobalScope.launch {
            documents.postValue(interactors.getListings())
        }
    }
}