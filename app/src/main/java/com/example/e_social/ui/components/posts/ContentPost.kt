package com.example.e_social.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.models.domain.model.PostEntry

@Composable
fun ContentPost(postEntry: PostEntry) {
   Column{
       Column(modifier = Modifier.padding(8.dp)) {
           TitlePost(postEntry.title)
           TextContent(postEntry.content)
       }
       if(postEntry.images.isNotEmpty())
       ImageContent(postEntry.images[0])
   }
}

@Composable
fun ImageContent(url: String) {
    Image(
        painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).placeholder(R.drawable.placeholder_image).build()),
        contentDescription = null,
        contentScale = ContentScale.Crop,
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