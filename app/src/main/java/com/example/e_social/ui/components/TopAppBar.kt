package com.example.e_social.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
//import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.ui.screens.UserViewModel


@Composable
fun TopApp(userViewModel: UserViewModel = hiltViewModel(), title:String, icon:ImageVector, onIconClick:()->Unit){
    val searchBarState  = userViewModel.searchBarState.value
    val searchValue = userViewModel.searchValue.value
    val painter = rememberImagePainter(data ="https://gaplo.tech/content/images/2020/03/android-jetpack.jpg", builder = {
        crossfade(true)
        placeholder(R.drawable.placeholder_image)
        transformations(CircleCropTransformation())
    } )
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
                searchValue=searchValue,
                onSearchValueChange = {userViewModel.onSearchChange(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearchBarStateChange= {userViewModel.onSearchBarStateChange(it)}
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
                        userViewModel.onSearchBarStateChange(true)
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
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(30.dp).align(Alignment.CenterVertically)
            )
        }
    }

}