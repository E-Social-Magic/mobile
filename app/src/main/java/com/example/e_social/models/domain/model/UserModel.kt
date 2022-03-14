package com.example.e_social.models.domain.model

import com.example.e_social.models.data.response.Subject

data class UserModel(
    val username: String,
    val password: String?=null,
    val email: String,
    val id: String,
    val role: String,
    val token: String,
    val updatedAt: String,
    )