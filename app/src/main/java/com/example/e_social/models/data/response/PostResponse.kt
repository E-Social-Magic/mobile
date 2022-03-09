package com.example.e_social.models.data.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    val comments: List<Comment>,
    val content: String,
    val createdAt: String,
    val id: String,
    val images: List<String>,
    val videos:List<String>,
    val title: String,
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String,
    val visible: Int
)
