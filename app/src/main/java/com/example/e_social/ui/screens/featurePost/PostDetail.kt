package com.example.e_social.ui.screens.featurePost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_social.ui.components.SimpleTopAppBar
import com.example.e_social.ui.components.posts.HeaderPost
import com.example.e_social.ui.components.posts.TextContent
import com.example.e_social.ui.components.posts.TitlePost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(route = "post_detail")
@Composable
fun PostDetail(
    navigator: DestinationsNavigator,
    title: String,
    content: String,
//    images: List<String>?,
    authorAvatar: String,
    userName: String,
    createdAt: String,
) {

    var shouldShowComment by remember { mutableStateOf(false) }

    Column {
        SimpleTopAppBar(title = "Post Detail") {
            navigator.navigateUp()
        }
        Card(
            shape = RoundedCornerShape(8.dp), elevation = 6.dp, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                HeaderPost(
                    authorAvatar = authorAvatar,
                    userName = userName,
                    createdAt = createdAt
                )
                TitlePost(title)
                TextContent(content)
            }
//        LazyColumn {
//            items(images.size) { index ->
//                ImageContent(url = images[index])
//            }
//        }
        }
    }

}