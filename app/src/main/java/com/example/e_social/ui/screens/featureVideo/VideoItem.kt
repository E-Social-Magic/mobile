package com.example.e_social.ui.screens.featureVideo

import androidx.compose.runtime.Immutable

@Immutable
data class VideoItem(
    val id: String,
    val title:String,
    val mediaUrl: String,
    val thumbnail: String,
    val lastPlayedPosition: Long = 0,
    val content: String,
)
