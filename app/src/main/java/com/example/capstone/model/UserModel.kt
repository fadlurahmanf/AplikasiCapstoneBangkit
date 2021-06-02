package com.example.capstone.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
        var email:String?=null,
        var fullName:String?=null,
        var phoneNumber:String?=null,
        var password:String?=null,
        var imageProfile:String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(fullName)
        parcel.writeString(phoneNumber)
        parcel.writeString(password)
        parcel.writeString(imageProfile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}