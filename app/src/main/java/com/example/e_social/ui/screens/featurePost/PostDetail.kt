package com.example.e_social.ui.screens.featurePost

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.ui.components.CircularProgressBar
import com.example.e_social.ui.components.SimpleTopAppBar
import com.example.e_social.ui.components.posts.HeaderPost
import com.example.e_social.ui.components.posts.ImageContent
import com.example.e_social.ui.components.posts.TextContent
import com.example.e_social.ui.components.posts.TitlePost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Destination(route = "post_detail")
@Composable
fun PostDetail(
    navigator: DestinationsNavigator,
    postViewModel: PostViewModel = hiltViewModel(),
    postId: String
) {

    var shouldShowComment by remember { mutableStateOf(false) }
    val coroutineScope= rememberCoroutineScope()
    var isLoading by remember{ mutableStateOf( true)}
    val post = postViewModel.post.value
    LaunchedEffect(key1 = true ){
        postViewModel.findPostById(id = postId)
             isLoading=false

    }
    if (isLoading) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            CircularProgressBar(isDisplay = isLoading)
        }
    } else
            Column {
                SimpleTopAppBar(title = "Chi tiết bài viết") {
                    navigator.navigateUp()
                }
                if (post == null)
                    Text(text = "Không tìm thấy bài viết này", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
                else
                Card(
                    shape = RoundedCornerShape(8.dp), elevation = 6.dp, modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp).padding(bottom = 50.dp)) {
                        HeaderPost(
                            navigator=navigator,
                            userId=post!!.userId,
                            authorAvatar = post!!.authorAvatar,
                            userName = post!!.userName,
                            createdAt = post!!.createdAt,
                            coins = post!!.coins,
                            costs = post!!.costs
                        )
                        TitlePost(post!!.title)
                        TextContent(post!!.content)
                        LazyColumn {
                            items(post!!.images.size) { index ->
                                ImageContent(url = post!!.images[index])
                            }
                        }
                    }

                }
            }

}