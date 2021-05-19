package com.example.projekaplikasibangkitcapstone.utils.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_EMAIL
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import java.sql.SQLException

class DataHelper(context: Context) {
    private var dataBaseHelper:DatabaseHelper = DatabaseHelper(context)
    private lateinit var database:SQLiteDatabase
    companion object{
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE:DataHelper? = null
        fun getInstance(context: Context):DataHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: DataHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open(){
        database = dataBaseHelper.writableDatabase
    }
    fun close(){
        dataBaseHelper.close()
        if (database.isOpen){
            database.close()
        }
    }

    fun update(email:String, values: ContentValues?):Int{
        dataBaseHelper.writableDatabase
        return database.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(email.toString()))
    }

    fun getAllData():Cursor?{
        return database.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    fun insert(values:ContentValues?):Long{
        dataBaseHelper.writableDatabase
        return database.insert(
                DATABASE_TABLE,
                null,
                values
        )
    }
    fun deleteByEmail(email: String):Int{
        dataBaseHelper.writableDatabase
        return database.delete(DATABASE_TABLE, "$COL_EMAIL = '$email'", null)
    }

    fun queryByEmail(email: String):Cursor{
        return database.query(DATABASE_TABLE, null, "$COL_EMAIL=?", arrayOf(email.toString()), null, null, null, null)
    }

    fun getDataByEmail(email: String):Cursor{
        return database.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL='$email'",null)
    }
}