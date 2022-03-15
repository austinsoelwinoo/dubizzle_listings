package com.dubizzle.listings

import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.presentation.components.UIOption
import com.dubizzle.listings.presentation.utils.NTuple5
import com.dubizzle.listings.presentation.utils.filterListings
import com.dubizzle.listings.util.generateListings
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ListingDataFilterTest {
    private lateinit var nTuple5: NTuple5<String, Boolean, UIOption, UIOption, List<Listing>>

    @Before
    fun init() {
        nTuple5 = NTuple5(
            "",
            false,
            UIOption.DISPLAY_SIMPLE,
            UIOption.SORT_DATE_O_N,
            generateListings().results
        )
    }


    @Test
    fun check_Empty() {
        Assert.assertNotNull(nTuple5)
        Assert.assertEquals(20, nTuple5.t5.size)
    }

    @Test
    fun filterListings_search_data() {
        val searchQuery = "a"
        val result = nTuple5.copy(t1 = searchQuery).filterListings()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(11, result.values.flatten().size)
        for (listing in result.values.flatten()) {
            Assert.assertTrue(listing.name.contains(searchQuery))
        }
    }

    @Test
    fun filterListings_search_empty() {
        val searchQuery = "random query"
        val result = nTuple5.copy(t1 = searchQuery).filterListings()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(0, result.values.flatten().size)
    }

    @Test
    fun filterListings_SORT_PRICE_L_H() {
        val sortOption = UIOption.SORT_PRICE_L_H
        val result = nTuple5.copy(t4 = sortOption).filterListings()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(20, result.values.flatten().size)
        var prevPrice: Int? = null
        for (listing in result.values.flatten()) {
            if (prevPrice != null) {
                Assert.assertTrue(prevPrice <= listing.extractPriceValue())
            }
            prevPrice = listing.extractPriceValue()
        }
    }

    @Test
    fun filterListings_SORT_DATE_O_N() {
        val sortOption = UIOption.SORT_DATE_O_N
        val result = nTuple5.copy(t4 = sortOption).filterListings()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(20, result.values.flatten().size)
        var prevTime: Long? = null
        for (listing in result.values.flatten()) {
            if (prevTime != null) {
                Assert.assertNotNull(listing.extractCreatedAtDateValue())
                Assert.assertTrue(prevTime <= listing.extractCreatedAtDateValue()?.time!!)
            }
            prevTime = listing.extractCreatedAtDateValue()?.time ?: 0
        }
    }

    @Test
    fun filterListings_SORT_DATE_N_O() {
        val sortOption = UIOption.SORT_DATE_N_O
        val result = nTuple5.copy(t4 = sortOption).filterListings()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(20, result.values.flatten().size)
        var prevTime: Long? = null
        for (listing in result.values.flatten()) {
            if (prevTime != null) {
                Assert.assertNotNull(listing.extractCreatedAtDateValue())
                Assert.assertTrue(prevTime >= listing.extractCreatedAtDateValue()?.time!!)
            }
            prevTime = listing.extractCreatedAtDateValue()?.time ?: 0
        }
    }


    @Test
    fun filterListings_Group_By() {
        val singleGroup = nTuple5.filterListings()
        Assert.assertEquals(1, singleGroup.size)
        Assert.assertTrue(singleGroup.keys.first().isEmpty())

        val grouped = nTuple5.copy(t2 = true).filterListings()
        Assert.assertEquals(8, grouped.size)
        for (dateKey in grouped.keys) {
            Assert.assertTrue(dateKey.isNotEmpty())
        }
    }
}