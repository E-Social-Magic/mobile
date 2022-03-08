package com.example.e_social.ui.components.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.ui.screens.featurePost.PostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PostEntry(post:PostEntry,navigator: DestinationsNavigator,modifier: Modifier=Modifier,postViewModel: PostViewModel= hiltViewModel()){
    Card(shape = RoundedCornerShape(8.dp),elevation = 6.dp, modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp)
        .padding(top = 8.dp)) {
        Column {
            HeaderPost(post)
            ContentPost(post)
            BottomPostAction(post)

        }
    }
}