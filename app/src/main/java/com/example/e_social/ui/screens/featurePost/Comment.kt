package com.example.e_social.ui.screens.featurePost

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
//import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.models.domain.model.Message
import com.example.e_social.ui.theme.Grey100
import com.example.e_social.ui.theme.Grey200


@Composable
fun ListComment(postViewModel: PostViewModel= hiltViewModel(),messages: List<Message>){
    ListCommentEntry(messages = messages)
}

@Composable
fun ListCommentEntry(messages: List<Message>){
    if (messages.isNotEmpty())
        messages.map {message ->
            MessageCard(message)
    }
}

@Composable
fun MessageCard(msg: Message) {
    val painter = rememberImagePainter(data =msg.avatarAuthor, builder = {
        crossfade(true)
        placeholder(R.drawable.placeholder_image)
        error(R.drawable.default_avatar)
        transformations(CircleCropTransformation())
    } )
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .background(color = Color(0xFFDBECFE), shape = RoundedCornerShape(12.dp))) {
            Text(
                text = msg.authorName,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.message,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
            if (!msg.images.isNullOrEmpty()){
                msg.images.map { image->
                    Image(
                        painter = rememberImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = image)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.default_image)
                                .apply(block = fun ImageRequest.Builder.() {
                                    transformations(CircleCropTransformation())
                                }).build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.5.dp, MaterialTheme.colors.secondaryVariant)
                    )
                }
            }
        }
    }
}

@Composable
fun CommentInput(modifier: Modifier=Modifier,commentValue:String, hint: String,onCommentValueChange:(String)->Unit){
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val painter = rememberImagePainter(data ="user avatar", builder = {
        crossfade(true)
        placeholder(R.drawable.placeholder_image)
        error(R.drawable.default_avatar)
        transformations(CircleCropTransformation())
    } )
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(modifier = modifier.shadow(5.dp, CircleShape).background(color = Color(0xFFDBECFE)).wrapContentSize()) {
            TextField(
                value = commentValue,
                onValueChange = {
                    onCommentValueChange(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused
                    },
                keyboardActions = KeyboardActions(
                    onSearch = {

                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (commentValue.isNotEmpty()) {
                                onCommentValueChange("")
                            } else {
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = "Close Icon",
                            tint = Color(0xFF4CBB17)
                        )
                    }
                },
                placeholder =  {
                    Text(
                        text = hint,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                ),
            )

        }
    }
}