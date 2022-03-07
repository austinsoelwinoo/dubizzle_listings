package com.dubizzle.core.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.prettifiedDate(): String {
    val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH)
    val output = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
    return try {
        output.format(input.parse(this))
    } catch (e: ParseException) {
        e.printStackTrace()
        this
    }
}