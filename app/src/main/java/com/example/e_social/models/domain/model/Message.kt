package com.example.e_social.models.domain.model



data class Message(
    val authorName: String,
    val avatarAuthor: String?,
    val message: String,
    val images: List<String>
)
