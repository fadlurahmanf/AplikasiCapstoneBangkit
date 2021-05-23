package com.example.capstone.utils.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.capstone.utils.database.DatabaseHelper.Companion.DATABASE_NAME
import com.example.capstone.utils.database.DatabaseHelper.Companion.DATABASE_VERSION
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DisasterCaseHelper(context: Context):SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    lateinit var databaseSQlite:SQLiteDatabase
    companion object{
        private val DISASTER_CASE_TABLE_NAME = "disastercasedata"
    }

    @Throws(SQLiteException::class)
    fun open(){
        databaseSQlite = this.writableDatabase
    }
    fun insert(contentValues: ContentValues):Long{
        val database = this.writableDatabase
        return database.insert(DISASTER_CASE_TABLE_NAME, null, contentValues)
    }
}