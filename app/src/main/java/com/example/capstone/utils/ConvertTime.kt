package com.example.capstone.utils

import java.text.SimpleDateFormat
import java.util.*

object ConvertTime {
    fun getTimeByTimeStamp(timestamp:Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yy hh:mm a")
        val netDate = Date(timestamp)
        return simpleDateFormat.format(netDate)
    }
}