package com.example.e_social.models.domain.model

data class TopicIndexListEntry(
    val pokemonName: String,
    val imageUrl: String,
    val number: Int,
    val isCheck:Boolean=false
)