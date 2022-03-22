package com.example.e_social.models.domain.model

import java.io.File

data class PostModel(
    var title: String,
    var content: String,
    var hideName:Boolean=false,
    var costs: Boolean=false,
    var expired:Long=4075911643,
    var coins:Int=0,
    var files: List<File>?=null
)
