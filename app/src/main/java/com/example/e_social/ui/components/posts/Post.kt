package com.example.e_social.ui.components.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PostComponent(){
    Card(shape = MaterialTheme.shapes.large) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            HeaderPost()
            ContentPost()
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                BottomPostAction()
            }
        }
    }
}