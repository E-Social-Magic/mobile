package com.example.e_social.models.data.response


import com.google.gson.annotations.SerializedName

data class Topic(
    val id : String,
    @SerializedName("group_name")
    val groupName: String,
    val subject: String,
    val posts:List<String>,
    val users:List<String>,
    val avatar: String,
    val updatedAt: String,
    val createdAt: String,
    @SerializedName("user_id")
    val userId:String,
)
