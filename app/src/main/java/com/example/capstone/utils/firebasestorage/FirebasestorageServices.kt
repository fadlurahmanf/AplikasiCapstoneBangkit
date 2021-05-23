package com.example.capstone.utils.firebasestorage

import android.graphics.Bitmap
import android.net.Uri
import com.example.capstone.utils.firebasestorage.FirebasestorageObject.DisasterCaseDataStorage.Companion.DISASTER_CASE_FOLDER_NAME
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class FirebasestorageServices {
    val storageServices = FirebaseStorage.getInstance()
    inner class disasterCaseData{
        fun insertImageDisasterCase(imageName: String,imageFile:ByteArray): UploadTask {
            var storeLocation =  storageServices.getReference().child("$DISASTER_CASE_FOLDER_NAME/$imageName")
            return storeLocation.putBytes(imageFile)
        }
    }
}
