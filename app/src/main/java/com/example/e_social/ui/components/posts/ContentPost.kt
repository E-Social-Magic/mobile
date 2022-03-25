package com.example.e_social.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.e_social.R
import com.example.e_social.models.domain.model.PostEntry

@Composable
fun ContentPost(onClickAction: () -> Unit, postEntry: PostEntry) {
    val images = postEntry.images
    Column(modifier = Modifier.fillMaxWidth().clickable {
        onClickAction.invoke()
    }) {
        Column(modifier = Modifier.padding(8.dp)) {
            TitlePost(postEntry.title)
            TextContent(postEntry.content)
        }
        if (images.isNotEmpty())
           ImageContent(url = images[0])
    }

}


@Composable
fun ImageContent(url: String) {

    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            placeholder(R.drawable.placeholder_image)
            size(Int.MAX_VALUE)
        })
    Image(
        painter = painter,
        contentDescription = null,
        Modifier.fillMaxWidth(),
        contentScale = ContentScale.Fit
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