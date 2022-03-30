package com.example.e_social.models.data.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    val confirm: String,
    val email: String,
    val phone :String,
    val password: String,
    val username: String
)