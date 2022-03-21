package com.example.e_social.models.data.response

import com.google.gson.annotations.SerializedName

data class EditPostResponse(
    val post: PostResponse,
    var message: String?
)
