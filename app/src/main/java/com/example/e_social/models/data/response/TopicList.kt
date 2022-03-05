package com.example.e_social.models.data.response


import com.google.gson.annotations.SerializedName

data class TopicList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Topic>
)