package com.udacity.asteroidradar.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"

    fun getCurrentDate():String{
        val formatter = DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
        val current = LocalDateTime.now().format(formatter)
        return current
    }

    fun get7Date():String{
        val formatter = DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
        val current = LocalDateTime.now().plusDays(6).format(formatter)
        return current
    }
}