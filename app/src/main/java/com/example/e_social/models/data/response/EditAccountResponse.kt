package com.example.e_social.models.data.response

import com.google.gson.annotations.SerializedName

data class EditAccountResponse(
    var user: UserInfo,
    var message: String?
)
