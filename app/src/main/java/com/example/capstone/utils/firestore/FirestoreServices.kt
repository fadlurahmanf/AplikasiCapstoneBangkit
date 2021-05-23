package com.example.capstone.utils.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.model.UserModel
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_ID_CASE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.TABLE_DISASTER_CASE_DATA
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.EMAIL_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.TABLE_USER_DATA
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreServices {
    var db = FirebaseFirestore.getInstance()

    inner class UserData{
        fun insertUserData(userModel: MutableMap<String, Any>): Task<Void> {
            return db.collection(TABLE_USER_DATA).document(userModel[EMAIL_USER].toString()).set(userModel)
        }

        fun getUserDataByEmail(email:String): Task<DocumentSnapshot> {
            return db.collection(TABLE_USER_DATA).document(email).get()
        }
    }

    inner class DisasterCaseData{
        fun insertDisasterCaseData(disasterCaseDataModel:MutableMap<String, Any>): Task<Void> {
            return db.collection(TABLE_DISASTER_CASE_DATA).document(disasterCaseDataModel[COL_DISASTER_ID_CASE].toString()).set(disasterCaseDataModel)
        }
    }
}