package com.example.e_social.ui.components.posts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.e_social.R
import com.example.e_social.models.domain.model.PostEntry
import kotlinx.coroutines.launch

@Composable
fun ContentPost(onClickAction: () -> Unit, postEntry: PostEntry) {
    val images = postEntry.images
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClickAction.invoke()
        }) {
        Column(modifier = Modifier.padding(8.dp)) {
            TitlePost(postEntry.title)
            TextContent(postEntry.content)
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (images.isNotEmpty())
           ImageContent(url = images[0])
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageContent(url: String) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            size(Int.MAX_VALUE)
        })
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    if (showDialog) {
                Dialog(
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    ),
                    onDismissRequest = { setShowDialog(false) },
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    rotationZ = rotation,
                                    translationX = offset.x,
                                    translationY = offset.y
                                )
                                .transformable(state = state)
                                .fillMaxSize()
                        )
                    }
                }
    }
    else {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    setShowDialog(true)
                }
        )
    }
}

@Composable
fun ImageContentGrid(url: String) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            size(Int.MAX_VALUE)
        })
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp)
    )
}
@Composable
fun TextContent(text: String) {
    Text(
        text = text,
        color = Color.DarkGray,
        fontSize = 14.sp,
        maxLines = 3
    )
}

@Composable
fun TitlePost(text: String) {
    Text(
        text = text,
        maxLines = 3,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = Color.Black,
    )
}