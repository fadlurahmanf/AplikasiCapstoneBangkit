package com.example.capstone.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
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
    fun getUriFromBitmap(imageInBitmap:Bitmap, context: Context):Uri{
        var image = imageInBitmap
        var bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        var path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
        return Uri.parse(path) as Uri
    }

    fun getPathImage(imageInBitmap: Bitmap, context: Context): String? {
        var image = imageInBitmap
        var bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        var path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
        return path
    }
    fun getBitmapFromUri(imageInUri: Uri, context: Context): Bitmap? {
        var imageInBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageInUri)
        return imageInBitmap
    }
}