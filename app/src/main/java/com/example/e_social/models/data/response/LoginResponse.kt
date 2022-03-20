package com.example.e_social.models.data.response

data class LoginResponse(
    val email: String,
    val id: String,
    val role: String,
    val subjects: List<Topic>,
    val token: String,
    val updatedAt: String,
    val username: String,
    val message:String?,
)