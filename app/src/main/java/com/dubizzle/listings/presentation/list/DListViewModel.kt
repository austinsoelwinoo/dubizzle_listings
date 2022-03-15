package com.dubizzle.listings.presentation.list

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.presentation.components.UIOption
import com.dubizzle.listings.presentation.utils.mutableStateOf
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DListViewModel(private val interactors: Interactors, val state: SavedStateHandle) :
    ViewModel() {
    var text by state.mutableStateOf("")
    var isGroupChecked by state.mutableStateOf(false)
    var displayOptionItem by state.mutableStateOf(UIOption.DISPLAY_SIMPLE)
    var sortOptionItem by state.mutableStateOf(UIOption.SORT_DATE_O_N)
    var isLoading by state.mutableStateOf(false)

    var listings by state.mutableStateOf(listOf<Listing>())

    fun loadDocuments() {
        isLoading = true
        viewModelScope.launch {
            listings = interactors.getListings()
            isLoading = false
        }
    }

    val result by state.mutableStateOf(mapOf<String, List<Listing>>()) { valueLoadedFromState, setter ->
        snapshotFlow {
            NTuple5(
                text,
                isGroupChecked,
                displayOptionItem,
                sortOptionItem,
                listings
            )
        }
            .drop(if (valueLoadedFromState != null) 1 else 0)
            .map { nTuple ->
                val list = filterListings(nTuple)
                if (isGroupChecked)
                    list.groupBy { it.prettifiedCreatedAt() }
                else
                    mapOf(Pair("", list))
            }
            .onEach {
                setter(it)
            }
            .launchIn(viewModelScope)
    }

    private fun filterListings(
        nTuple4: NTuple5<String, Boolean, UIOption, UIOption, List<Listing>>
    ): List<Listing> {
        return nTuple4.t5.filter { it.name.contains(nTuple4.t1, true) }.sortBySortOption(nTuple4.t4)
    }
}

fun List<Listing>.sortBySortOption(uiOption: UIOption): List<Listing> {
    return when (uiOption) {
        UIOption.SORT_DATE_O_N -> this.sortedBy { it.extractCreatedAtDateValue() }
        UIOption.SORT_DATE_N_O -> this.sortedByDescending { it.extractCreatedAtDateValue() }
        UIOption.SORT_PRICE_L_H -> this.sortedBy { it.extractPriceValue() }
        UIOption.SORT_PRICE_H_L -> this.sortedByDescending { it.extractPriceValue() }
        else -> this
    }
}

data class NTuple5<T1, T2, T3, T4, T5>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5)