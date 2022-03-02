package com.example.e_social.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.e_social.ui.components.posts.PostComponent
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start=true)
@Composable
fun HomeScreen(){
    PostComponent()
}