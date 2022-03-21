package com.example.e_social.models.data.request

import java.io.File

data class EditPostRequest (
    var title: String,
    var content: String,
    var files: List<File>,
    var isHide:Boolean=false,
    var isCosts:Boolean=false,
    var expried:Long,
    var coins:Long=0
)