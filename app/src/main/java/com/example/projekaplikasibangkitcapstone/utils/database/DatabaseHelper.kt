package com.example.projekaplikasibangkitcapstone.utils.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_EMAIL
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PASSWORD
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PHONE_NUMBER
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.TABLE_NAME


internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "DATABASE_CAPSTONE_BANGKIT"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME (" +
                "'$COL_EMAIL' TEXT," +
                "'$COL_FULL_NAME' TEXT," +
                "'$COL_PASSWORD' TEXT," +
                "'$COL_PHONE_NUMBER' TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}