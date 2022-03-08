package com.example.e_social.models.domain.model

import com.google.gson.annotations.SerializedName

data class PostListEntry(
    val post:List<PostEntry>,
    val totalPages:Int,
    val currentPage:Int,
)
