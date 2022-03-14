package com.dubizzle.core

import com.dubizzle.core.domain.Listing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class ListingTest {
    private lateinit var dummyListing: Listing
    private lateinit var emptyListing: Listing

    @Before
    fun init() {
        dummyListing = Listing(
            "2019-02-24 02:34:54.942502",
            "AED 12",
            "coffee mug",
            "244fe7e3d6e54fd18c72f5ab75e34822",
            listOf("f12fbe72ae0d4ce281f53265f2d36e71"),
            listOf("https://dubai.dubizzle.com/imageUrls"),
            listOf("https://dubai.dubizzle.com/imageUrlsThumbnails"),
        )

        emptyListing = Listing()
    }

    @Test
    fun listing_retrieveFirstImageUrl() {
        assertEquals("https://dubai.dubizzle.com/imageUrls",dummyListing.retrieveFirstImageUrl())

        assertEquals("",emptyListing.retrieveFirstImageUrl())
    }

    @Test
    fun listing_retrieveFirstImageThumbnail() {
        assertEquals("https://dubai.dubizzle.com/imageUrlsThumbnails",dummyListing.retrieveFirstImageThumbnail())

        assertEquals("",emptyListing.retrieveFirstImageThumbnail())
    }


    @Test
    fun listing_prettifiedCreatedAt() {
        assertEquals( "February 24, 2019",dummyListing.prettifiedCreatedAt())

        assertEquals("",emptyListing.prettifiedCreatedAt())
    }

    @Test
    fun listing_extractPriceValue() {
        assertEquals( 12,dummyListing.extractPriceValue())
        assertEquals( 0,emptyListing.extractPriceValue())
    }

    @Test
    fun listing_extractCreatedAtDateValue() {
        assertEquals( "Sun Feb 24 02:50:36 ICT 2019",dummyListing.extractCreatedAtDateValue().toString())
        assertEquals( null,emptyListing.extractCreatedAtDateValue())
    }
}
