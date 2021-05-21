package com.example.capstone.utils.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.COL_EMAIL
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.USER_TABLE_NAME
import com.example.capstone.utils.database.DatabaseHelper.Companion.DATABASE_NAME
import com.example.capstone.utils.database.DatabaseHelper.Companion.DATABASE_VERSION
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import java.sql.SQLException

class UserHelper(context: Context):SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {
    lateinit var database:SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = USER_TABLE_NAME

        private var INSTANCE:UserHelper? = null
        fun getInstance(context: Context):UserHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: UserHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open(){
        database = this.writableDatabase
    }
//    fun close(){
//        database.close()
//        if (database.isOpen){
//            database.close()
//        }
//    }

    fun update(email:String, values: ContentValues?):Int{
        this.writableDatabase
        return database.update(USER_TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(email.toString()))
    }

    fun getAllData():Cursor?{
        return database.rawQuery("SELECT * FROM $USER_TABLE_NAME", null)
    }
    fun insert(values:ContentValues?):Long{
        this.writableDatabase
        return database.insert(
                DATABASE_TABLE,
                null,
                values
        )
    }
    fun deleteByEmail(email: String):Int{
        this.writableDatabase
        return database.delete(DATABASE_TABLE, "$COL_EMAIL = '$email'", null)
    }

    fun queryByEmail(email: String):Cursor{
        return database.query(DATABASE_TABLE, null, "$COL_EMAIL=?", arrayOf(email.toString()), null, null, null, null)
    }

    fun getDataByEmail(email: String):Cursor{
        return database.rawQuery("SELECT * FROM $USER_TABLE_NAME WHERE $COL_EMAIL='$email'",null)
    }
}