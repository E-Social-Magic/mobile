package com.example.e_social.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.ui.theme.Shapes
import com.example.e_social.viewmodels.UserViewModel


@Composable
fun TopApp(userViewModel: UserViewModel= hiltViewModel(), title:String, icon:ImageVector, onIconClick:()->Unit){
    var searchBarState by remember{ mutableStateOf(false)}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .background(color = MaterialTheme.colors.primarySurface)
            .shadow(elevation = 1.dp)
            .padding(horizontal = 16.dp)
            .clickable { }
    ) {
        if (searchBarState)
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            }
        else {
            Text(
                text = title,
                color = White,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.15.sp
                ),
            )
            Image(
                imageVector = icon,
                contentDescription = "Search Icon",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .clickable(onClick = {
                        onIconClick.invoke()
                        searchBarState =true
                    })
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Image(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications Icon",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .clickable(onClick = onIconClick)
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://gaplo.tech/content/images/2020/03/android-jetpack.jpg")
                        .apply(block = fun ImageRequest.Builder.() {
                            transformations(CircleCropTransformation())
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier.size(30.dp).align(Alignment.CenterVertically)
            )
        }
    }

}