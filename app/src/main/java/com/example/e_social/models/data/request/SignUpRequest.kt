package com.example.e_social.models.data.request

data class SignUpRequest(
    val confirm_password: String,
    val email: String,
    val password: String,
    val username: String
)