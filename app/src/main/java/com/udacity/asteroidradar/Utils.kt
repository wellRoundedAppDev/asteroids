package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*




var localTimeNow = LocalDateTime.now()

var year = localTimeNow.year.toString()
var month = localTimeNow.month.value.toString()
var day = localTimeNow.dayOfMonth.toString()

fun getTimeNowInStringFormat(): String{

    if(month.length == 1){
        month = "0$month"
    }
    return "$year-$month-$day"
}