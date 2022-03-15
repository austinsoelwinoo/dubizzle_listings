package com.dubizzle.listings.presentation.utils

import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.presentation.components.UIOption

fun NTuple5<String, Boolean, UIOption, UIOption, List<Listing>>.filterListings(): Map<String, List<Listing>> {
    val list = t5.filter { it.name.contains(t1, true) }.sortBySortOption(t4)
    return if (t2)
        list.groupBy { it.prettifiedCreatedAt() }
    else
        mapOf(Pair("", list))
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