package com.example.capstone.utils.firebasestorage

import android.graphics.Bitmap
import android.net.Uri
import com.example.capstone.utils.firebasestorage.FirebasestorageObject.DisasterCaseDataStorage.Companion.DISASTER_CASE_FOLDER_NAME
import com.example.capstone.utils.firebasestorage.FirebasestorageObject.DisasterCaseDataStorage.Companion.USERDATA_FOLDER_NAME
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class FirebasestorageServices {
    val storageServices = FirebaseStorage.getInstance()

    inner class disasterCaseData{
        fun insertImageDisasterCase(imageName: String,imageFile:ByteArray): UploadTask {
            var storeLocation =  storageServices.getReference().child("$DISASTER_CASE_FOLDER_NAME/$imageName")
//            var storeLocation =  storageServices.getReferenceFromUrl("gs://ancana-b21-cap0252.appspot.com/").child("$DISASTER_CASE_FOLDER_NAME/$imageName")
            return storeLocation.putBytes(imageFile)
        }
        fun getImageURLByName(name:String): Task<Uri> {
            return storageServices.getReference().child("disasterCaseData/${name}").downloadUrl
        }
    }
    inner class userData{
        fun insertImageProfileUser(imageName: String, imageFile: ByteArray): UploadTask {
            var storeLocation = storageServices.getReference().child("$USERDATA_FOLDER_NAME/$imageName")
            return storeLocation.putBytes(imageFile)
        }
        fun getImageURLbyName(imageName: String): Task<Uri> {
            var storeLocation = storageServices.getReference().child("$USERDATA_FOLDER_NAME/$imageName").downloadUrl
            return storeLocation
        }
    }
}
