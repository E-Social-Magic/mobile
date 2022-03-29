package com.example.e_social.models.domain.model

import com.example.e_social.models.Constants


data class Message(
    var authorName: String?=Constants.Anonymous,
    var avatarAuthor: String?=null,
    var message: String,
    val voteups:List<String> = listOf(),
    val votedowns:List<String> = listOf(),
    val votes:Int=0,
    var images: List<String>?=null,
    val isCorrect :Boolean,
    val userId:String,
    val id :String
)
