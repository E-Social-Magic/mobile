package com.example.e_social.ui.components.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.ui.screens.destinations.PostDetailDestination
import com.example.e_social.ui.screens.featurePost.CommentInput
import com.example.e_social.ui.screens.featurePost.ListComment
import com.example.e_social.ui.screens.featurePost.PostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PostEntry(
    post: PostEntry,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    postViewModel: PostViewModel
) {

    var shouldShowComment by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp), elevation = 6.dp, modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 8.dp)
    ) {
        Column {
            HeaderPost(
                authorAvatar = post.authorAvatar,
                userName = post.userName,
                createdAt = post.createdAt
            )
            ContentPost(onClickAction = {
                navigator.navigate(PostDetailDestination(postId = post.id))
            }, post)
            BottomPostAction(
                post,
                postViewModel = postViewModel,
                onCommentIconClick = { shouldShowComment = !shouldShowComment })
            if (shouldShowComment) {
                CommentInput(
                    hint = "comment something",
                    postId=post.id,
                    onSubmit = {postId,comment-> postViewModel.submitComment(postId = post.id, message = comment) },
                )
                ListComment(messages = post.comments)
            }
        }
    }
}