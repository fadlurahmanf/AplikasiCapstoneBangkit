package com.example.capstone.utils.firestore

object FirestoreObject {
    internal class UserDataTable{
        companion object{
            const val TABLE_USER_DATA = "userData"
            const val FULL_NAME = "full_name"
            const val EMAIL_USER = "email"
            const val PHONE_NUMBER = "phone_number"
            const val PASSWORD_USER = "password"
            const val IMAGE_PROFILE_USER = "imageProfile"
        }
    }

    internal class DisasterCaseDataTable{
        companion object{
            const val TABLE_DISASTER_CASE_DATA = "disasterCaseData"
            const val COL_DISASTER_ID_CASE = "_id"
            const val COL_DISASTER_DATE_TIME = "date_time"
            const val COL_REPORT_BY_EMAIL = "email"
            const val COL_REPORT_BY_PHONE_NUMBER = "phone_number"
            const val COL_DISASTER_IMAGE_CASE = "image"
            const val COL_DISASTER_CASE_LOCATION = "location"
            const val COL_DISASTER_LATITUDE = "latitude"
            const val COL_DISASTER_LONGITUDE = "longitude"
            const val COL_DISASTER_TYPE = "tipe_bencana"
            const val COL_DISASTER_CASE_DETAIL = "detail"
            const val COL_DISASTER_CASE_STATUS = "status"
        }
    }
    internal class LangkahPertamaData{
        companion object{
            const val TABLE_LANGKAH_PERTAMA_DATA = "pertolonganPertamaData"
            const val LIST_TODO = "listTodo"
            const val DETAIL1 = "detail1"
            const val TODO1 = "todo1"
            const val DETAIL2 = "detail2"
            const val TODO2 = "todo2"
            const val DETAIL3 = "detail3"
            const val TODO3 = "todo3"
            const val DETAIL4 = "detail4"
            const val TODO4 = "todo4"
            const val DETAIL5 = "detail5"
            const val TODO5 = "todo5"
        }
    }
}