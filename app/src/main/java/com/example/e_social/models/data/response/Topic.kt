package com.example.e_social.models.data.response


import com.google.gson.annotations.SerializedName

data class Topic(
    val id : String,
    @SerializedName("group_name")
    val groupName: String,
    val subject: String,
    val posts:List<String>,
    @SerializedName("private_dt")
    val privateData: List<String>,
    @SerializedName("user_id")
    val users:List<String>,
    val visible: Int,
    val avatar: String,
    val updatedAt: String,
    val createdAt: String,
)
