package com.dubizzle.listings.presentation.list

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.presentation.components.UIOption
import com.dubizzle.listings.presentation.utils.NTuple5
import com.dubizzle.listings.presentation.utils.filterListings
import com.dubizzle.listings.presentation.utils.mutableStateOf
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DListViewModel(val interactors: Interactors, val state: SavedStateHandle) :
    ViewModel() {

    var text by state.mutableStateOf("")
    var isGroupChecked by state.mutableStateOf(false)
    var displayOptionItem by state.mutableStateOf(UIOption.DISPLAY_SIMPLE)
    var sortOptionItem by state.mutableStateOf(UIOption.SORT_DATE_O_N)
    var listings by state.mutableStateOf(listOf<Listing>())

    var isLoading by state.mutableStateOf(false)

    val result by state.mutableStateOf(mapOf<String, List<Listing>>()) { valueLoadedFromState, setter ->
        snapshotFlow { NTuple5(text, isGroupChecked, displayOptionItem, sortOptionItem, listings) }
            .drop(if (valueLoadedFromState != null) 1 else 0)
            .map { nTuple ->
                nTuple.filterListings()
            }
            .onEach {
                setter(it)
            }
            .launchIn(viewModelScope)
    }

    fun loadDocuments() {
        isLoading = true
        viewModelScope.launch {
            listings = interactors.getListings()
            isLoading = false
        }
    }
}
