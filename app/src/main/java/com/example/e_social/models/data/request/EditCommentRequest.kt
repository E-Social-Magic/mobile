package com.example.e_social.models.data.request

import java.io.File

data class EditCommentRequest(
    var comment: String,
    var files: List<File>,
)
