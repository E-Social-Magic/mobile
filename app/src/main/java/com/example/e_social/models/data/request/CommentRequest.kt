package com.example.e_social.models.data.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.io.File

data class CommentRequest(
    @Part("comment")
    val comment: RequestBody,
)
