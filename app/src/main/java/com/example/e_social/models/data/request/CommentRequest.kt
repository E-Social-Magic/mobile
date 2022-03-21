package com.example.e_social.models.data.request

import java.io.File

data class CommentRequest(
    var comment: String,
    var files: List<File>,
)
