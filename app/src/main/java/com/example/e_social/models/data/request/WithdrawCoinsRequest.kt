package com.example.e_social.models.data.request

data class WithdrawCoinsRequest(
    val amount: String,
    val phone: String,
    val displayName: String
)