package com.example.e_social.models.data.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.io.File

data class EditPostRequest (
    @Part("title") val title: RequestBody,
    @Part("content") val content: RequestBody,
    @Part("isHide") val isHide: RequestBody,
    @Part("isCosts") val isCosts: RequestBody,
    @Part("expried") val expried: RequestBody,
    @Part("coins") val coins: RequestBody,
    @Part val files: List<MultipartBody.Part>?
)