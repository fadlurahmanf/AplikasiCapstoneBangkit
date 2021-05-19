package com.example.projekaplikasibangkitcapstone.utils.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.projekaplikasibangkitcapstone"
    const val SCHEME = "content"

    internal class UserColumns:BaseColumns{
        companion object{
            const val TABLE_NAME = "userdata"
            const val COL_EMAIL = "email"
            const val COL_FULL_NAME = "full_name"
            const val COL_PHONE_NUMBER = "phone_number"
            const val COL_PASSWORD = "password"
        }
    }

    internal class DisasterColumns:BaseColumns{
        companion object{
            const val TABLE_NAME = "disasterdata"
            const val COL_ID_DISASTER_CASE = "_id"
            const val COL_LOCATION = "location"
            const val COL_DISASTER_TYPE = "type"
            const val COL_DETAIL_DISASTER_CASE = "detail"
            const val COL_REPORT_BY ="reportBy"
            const val COL_DATE_REPORT = "date"
            const val COL_STATUS = "status"
        }
    }
}