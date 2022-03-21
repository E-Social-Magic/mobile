package com.example.e_social.models.data.request

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class NewPostRequest constructor(
    var title: String,
    var content: String,
    var files: List<File>,
    var isHide:Boolean=false,
    var isCosts:Boolean=false,
    var expried:Long?=null,
    var coins:Long=0
)
