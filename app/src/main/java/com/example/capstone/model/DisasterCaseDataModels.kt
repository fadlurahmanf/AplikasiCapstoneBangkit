package com.example.capstone.model

import android.os.Parcel
import android.os.Parcelable

data class DisasterCaseDataModels(
        var disasterCaseID:String?="",
        var disasterType:String?="",
        var reportByEmail:String?="",
        var disasterCaseDataPhoto:String?="",
        var disasterLocation:String?="",
        var disasterLatitude:String?="",
        var reportByPhoneNumber:String?="",
        var disasterLongitude:String?="",
        var disasterCaseStatus:String?="",
        var disasterDateTime:String?="",
        var disasterCaseDetail:String?=""
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(disasterCaseID)
                parcel.writeString(disasterType)
                parcel.writeString(reportByEmail)
                parcel.writeString(disasterCaseDataPhoto)
                parcel.writeString(disasterLocation)
                parcel.writeString(disasterLatitude)
                parcel.writeString(reportByPhoneNumber)
                parcel.writeString(disasterLongitude)
                parcel.writeString(disasterCaseStatus)
                parcel.writeString(disasterDateTime)
                parcel.writeString(disasterCaseDetail)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<DisasterCaseDataModels> {
                override fun createFromParcel(parcel: Parcel): DisasterCaseDataModels {
                        return DisasterCaseDataModels(parcel)
                }

                override fun newArray(size: Int): Array<DisasterCaseDataModels?> {
                        return arrayOfNulls(size)
                }
        }
}