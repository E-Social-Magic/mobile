package com.example.e_social.models.data.request

data class EditAccountRequest(
    var address: String,
    var phone: String,
    var payment_id: String,
    var description: String,
    var level: String,
    var avatar: String
)
