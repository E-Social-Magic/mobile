package com.example.e_social.models.data.response

data class PostListResponse(
    val posts: List<PostResponse>,
    val totalPages:Int,
    val currentPage:Int,
)