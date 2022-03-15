package com.dubizzle.listings

import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.presentation.components.UIOption
import com.dubizzle.listings.presentation.utils.NTuple5
import com.dubizzle.listings.util.generateListings
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ListingDataFilterTest {
    private lateinit var nTuple5: NTuple5<String, Boolean, UIOption, UIOption, List<Listing>>

    @Before
    fun init() {
        nTuple5 = NTuple5("",false,UIOption.DISPLAY_SIMPLE,UIOption.SORT_DATE_O_N,generateListings().results)
    }


    @Test
    fun listing_retrieveFirstImageUrl() {
        Assert.assertEquals(20, nTuple5.t5.size)
    }
}