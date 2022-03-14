package com.example.e_social.models.data.response


import com.google.gson.annotations.SerializedName

data class TopicList(
    val groups: List<Topic>,
    val totalPages: Int,
    val currentPage: Int,
)