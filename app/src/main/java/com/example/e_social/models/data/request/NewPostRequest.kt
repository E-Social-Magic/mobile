package com.example.e_social.models.data.request

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.File

data class NewPostRequest(
    var title: String,
    var content: String,
    var files: List<File>,
    var isHide:Boolean=false,
)
