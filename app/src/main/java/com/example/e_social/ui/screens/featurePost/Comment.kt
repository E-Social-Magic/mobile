package com.example.e_social.ui.screens.featurePost

//import coil.compose.rememberAsyncImagePainter
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.e_social.R
import com.example.e_social.models.Constants
import com.example.e_social.models.domain.model.Message
import com.example.e_social.ui.theme.Blue700
import com.example.e_social.util.FileUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.File


@Composable
fun ListComment(postViewModel: PostViewModel = hiltViewModel(), messages: List<Message>) {
    ListCommentEntry(messages = messages)
}

@Composable
fun ListCommentEntry(messages: List<Message>) {
    if (messages.isNotEmpty())
        messages.mapIndexed { index, message ->
            if (index < 5)
                MessageCard(message)
        }
}

@Composable
fun MessageCard(msg: Message) {
    val painter = rememberImagePainter(data = msg.avatarAuthor, builder = {
        crossfade(true)
        placeholder(R.drawable.placeholder_image)
        error(R.drawable.default_avatar)
        transformations(CircleCropTransformation())
    })
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
        Column {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .background(color = Color(0xFFDBECFE), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)) {
                Text(
                    text = msg.authorName ?: Constants.Anonymous,
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
            }
            if (!msg.images.isNullOrEmpty()) {
                Image(
                    painter = rememberImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = msg.images!![0])
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.default_image)
                            .apply(block = fun ImageRequest.Builder.() {
                                transformations(RoundedCornersTransformation(10f))
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.5.dp, Color.Transparent)

                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun CommentInput(
    modifier: Modifier = Modifier,
    postId: String,
    hint: String = "",
    onSubmit: (String, Message) -> Unit
) {
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val storePermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_MEDIA_LOCATION)
    val context = LocalContext.current
    val painter = rememberImagePainter(data = "author avart", builder = {
        crossfade(true)
        placeholder(R.drawable.placeholder_image)
        error(R.drawable.default_avatar)
        transformations(CircleCropTransformation())
    })

    val comment = remember { mutableStateOf(Message(message = "")) }

    Column {
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
            Surface(
                modifier = modifier
                    .shadow(5.dp, CircleShape)
                    .background(color = Color(0xFFDBECFE))
                    .wrapContentSize()
            ) {
                TextField(
                    value = comment.value.message,
                    onValueChange = {
                        comment.value = comment.value.copy(message = it)
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
                        Row {
                            IconButton(
                                onClick = {
                                    storePermissionState.launchPermissionRequest()
                                    TedImagePicker.with(context)
                                        .startMultiImage { uriList ->
                                            uriList.map { uri ->
                                                FileUtils.getFile(
                                                    context = context,
                                                    uri)}
                                                .let {
                                                    comment.value = comment.value.copy(images = (it as List<File>).map { file -> file.absolutePath })
                                                }
                                        }

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoLibrary,
                                    contentDescription = "Close Icon",
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (comment.value.message.isNotEmpty()) {
                                        onSubmit.invoke(postId, comment.value.copy())
                                        comment.value.message = ""
                                        comment.value.images=null
                                    } else {

                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send Icon",
                                    tint = Blue700
                                )
                            }
                        }

                    },
                    placeholder = {
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
        if (!comment.value.images.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .heightIn(max = 150.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    comment.value.images!!.mapIndexed { index,file ->
                        if (index<3)
                        Image(
                            painter = rememberImagePainter(
                                data = File(file),
                                builder = {
                                    crossfade(true)
                                    placeholder(R.drawable.placeholder_image)
                                    error(R.drawable.default_avatar)
                                    transformations(RoundedCornersTransformation(12.5f))
                                }),
                            contentDescription = null,
                            modifier = Modifier
                                .size(75.dp)
                        )
                    }
                }
            }
        }
    }
}