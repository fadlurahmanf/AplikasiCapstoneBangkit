package com.example.projekaplikasibangkitcapstone.utils.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns:BaseColumns{
        companion object{
            const val TABLE_NAME = "userdata"
            const val COL_EMAIL = "email"
            const val COL_FULL_NAME = "full_name"
            const val COL_PHONE_NUMBER = "phone_number"
            const val COL_PASSWORD = "password"
        }
    }
}