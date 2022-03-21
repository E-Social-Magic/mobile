package com.example.e_social.models.data.response

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("username")
    var userName: String,
    var email: String,
    var avatar: String,
    var address: String?,
    var phone: String?,
    var description: String?,
    var subjects: List<Topic>,
    var coins: Long,
    var level: String,
    var id: String,
    var token: String?
)
