package com.dubizzle.core

import com.dubizzle.core.common.prettifiedDate
import org.junit.Assert
import org.junit.Test

class DateUtilsTest {

    @Test
    fun prettifiedDate_CorrectDate_ReturnsFormattedDate() {
        Assert.assertEquals("February 24, 2019", "2019-02-24 02:34:54.942502".prettifiedDate())
        Assert.assertEquals("April 15, 2019", "2019-04-15 13:38:53.687469".prettifiedDate())
        Assert.assertEquals("March 7, 2019", "2019-03-07 16:41:41.115808".prettifiedDate())
    }

    @Test
    fun prettifiedDate_incorrectDate_ReturnsOriginalValue() {
        Assert.assertEquals("", "".prettifiedDate())
        Assert.assertEquals("This is just text", "This is just text".prettifiedDate())
    }
}