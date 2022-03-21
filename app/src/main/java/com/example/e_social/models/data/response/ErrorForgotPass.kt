package com.example.e_social.models.data.response

data class ErrorForgotPass(
    val location: String,
    val msg: String,
    val `param`: String,
    val value: String
)