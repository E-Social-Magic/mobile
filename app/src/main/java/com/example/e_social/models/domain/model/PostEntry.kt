package com.example.e_social.models.domain.model

import com.google.gson.annotations.SerializedName

class PostEntry(
    val title:String,
    val content:String,
    val images:List<String>,
    @SerializedName("user_id")
    val userId:String,
    val visible:Int,
    val createdAt:String,
    val updatedAt:String
)