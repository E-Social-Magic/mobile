package com.example.e_social.models.data.request

data class PostRequest(
    val token:String,
    val offset:Int,
    val groupId: Int,
    val size:Int
)
