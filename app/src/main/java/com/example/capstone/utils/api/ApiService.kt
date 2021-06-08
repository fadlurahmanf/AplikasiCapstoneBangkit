package com.example.capstone.utils.api

import com.example.capstone.model.PostImageResponse
import com.squareup.okhttp.RequestBody
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.Call

interface ApiService {
    @Multipart
    @POST("predict")
    fun postImage(
            @Part file: MultipartBody.Part
    ):Call<PostImageResponse>
}