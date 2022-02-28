package com.example.e_social.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CircularProgressBar(isDisplay:Boolean){
    if (isDisplay){
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}