package com.example.e_social.models.domain.model

data class UserModel(
    val username: String,
    val password: String?=null,
    val email: String,
    val id: String,
    val role: String,
    val token: String,
    )