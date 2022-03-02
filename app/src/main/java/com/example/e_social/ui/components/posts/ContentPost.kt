package com.example.e_social.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.e_social.R

@Composable
fun ContentPost() {
    ImageContent("https://gaplo.tech/content/images/2020/03/android-jetpack.jpg")
}

@Composable
fun ImageContent(url: String) {
    val image = rememberImagePainter(
        data = url,
    )
    Image(
        painter =image,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}
