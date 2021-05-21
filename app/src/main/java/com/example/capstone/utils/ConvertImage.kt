package com.example.capstone.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object ConvertImage {
    fun getBytes(imageInBitmap:Bitmap):ByteArray{
        val stream = ByteArrayOutputStream()
        imageInBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
    fun getBitmap(imageInByteArray: ByteArray):Bitmap{
        return BitmapFactory.decodeByteArray(imageInByteArray, 0, imageInByteArray.size)
    }
}