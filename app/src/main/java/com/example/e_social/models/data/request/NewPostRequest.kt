package com.example.e_social.models.data.request

import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class NewPostRequest (
    @Part("title")
    val title: RequestBody,
    @Part("content")
    val content: RequestBody,
    @Part("hideName")
    val hideName: RequestBody,
    @Part("costs")
    val costs: RequestBody,
    @Part("expired")
    val expired: RequestBody,
    @Part("coins")
    val coins: RequestBody,
    @Part("group_id")
    val groupId:RequestBody
)

