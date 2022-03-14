package com.dubizzle.listings.presentation.list

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.presentation.utils.mutableStateOf
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class DListViewModel(private val interactors: Interactors, val state: SavedStateHandle) : ViewModel() {
    var text by state.mutableStateOf("")
    var isGroupChecked by state.mutableStateOf(false)
    var displayOptionItem by state.mutableStateOf(UIOption.DISPLAY_SIMPLE)
    var sortOptionItem by state.mutableStateOf(UIOption.SORT_DATE_O_N)

    val listings: MutableLiveData<List<Listing>> = MutableLiveData()

    fun loadDocuments() {
        viewModelScope.launch {
            listings.postValue(interactors.getListings())
        }
    }


    val result by state.mutableStateOf(emptyList<Listing>()) { valueLoadedFromState, setter ->
        snapshotFlow {
            NTuple4(
                text,
                isGroupChecked,
                displayOptionItem,
                sortOptionItem
            )
        }
            .drop(if (valueLoadedFromState != null) 1 else 0)
            .map {
                Timber.d("t1 ${it.t1} t2 ${it.t2} t3 ${it.t3} t4 ${it.t4} ")
                filterListings(listings, it)
            }
            .onEach {
                setter(it)
            }
            .launchIn(viewModelScope)
    }

    private fun filterListings(
        listings: MutableLiveData<List<Listing>>,
        nTuple4: NTuple4<String, Boolean, UIOption, UIOption>
    ): List<Listing> {
        val filterListings = listings.value ?: emptyList()
        return filterListings.filter { it.name.contains(nTuple4.t1) }
    }
}

data class NTuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)