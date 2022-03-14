package com.example.e_social.models.domain.model

data class TopicIndexListEntry(
    val id:String,
    val groupName: String,
    val avatar: String,
    val visible: Int,
    val isCheck:Boolean=false
)