package com.example.capstone.model

import com.google.gson.annotations.SerializedName

data class PostImageResponse(
    @SerializedName("disaster")
    var type:String
)