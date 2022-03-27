package com.example.e_social.ui.screens.featurePost

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.ui.components.ShimmerLoading
import com.example.e_social.ui.components.TopApp
import com.example.e_social.ui.components.posts.PostEntry
import com.example.e_social.ui.screens.destinations.ChooseGroupScreenDestination
import com.example.e_social.ui.screens.destinations.LoginScreenDestination
import com.example.e_social.ui.screens.destinations.ProfileScreenDestination
import com.example.e_social.ui.screens.destinations.SavePostScreenDestination
import com.example.e_social.ui.screens.featureGroup.GroupViewModel
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.theme.BackgroundBlue
import com.example.e_social.ui.theme.Grey100
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator,
    changeBarState: (Boolean) -> Unit,
    resultRecipient: ResultRecipient<ChooseGroupScreenDestination, String> ,
    postViewModel: PostViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel,
    groupViewModel: GroupViewModel = hiltViewModel()

) {
    val postList = postViewModel.postList.value
    val endReached = postViewModel.endReached.value
    val loadError = postViewModel.loadError.value
    val isLoading =postViewModel.isLoading.value
    val scrollState = rememberLazyListState()
    val scrollUpState = postViewModel.scrollUp.observeAsState()
    val selectedGroup = groupViewModel.selectedGroup.value
    val avatar = loginViewModel.user.value?.avatar
    resultRecipient.onResult {
        groupViewModel.onSelectedGroupId(it)
        postViewModel.refresh(it)
    }
    postViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    LaunchedEffect(key1 = loginViewModel.isLogin.value) {
        if (!loginViewModel.isLogin.value) {
            navigator.navigate(LoginScreenDestination)
        }
    }
    LaunchedEffect(key1 = true) {
        changeBarState.invoke(true)
    }
    Scaffold(
        modifier = Modifier.padding(bottom = 50.dp),
        topBar = {
            TopApp(
                navigator = navigator,
                title = "E-Social",
                icon = Icons.Outlined.Search,
                scrollUpState = scrollUpState
            )
            {
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    changeBarState.invoke(false)
                    navigator.navigate(SavePostScreenDestination)
                },
                contentColor = MaterialTheme.colors.background,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Note Button"
                    )
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
    {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(top = 8.dp)
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(6.5f),
                            shape = RoundedCornerShape(30)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = "avatar",
                                        builder = {
                                            crossfade(true)
                                            placeholder(R.drawable.placeholder_image)
                                            error(R.drawable.default_avatar)
                                            transformations(CircleCropTransformation())
                                            size(30)
                                        }),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterVertically)
                                        .clip(shape = CircleShape)
                                        .clickable {
                                            navigator.navigate(ProfileScreenDestination)
                                        },
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = selectedGroup?.groupName?:"Tất cả", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = selectedGroup?.groupName?:"Đang theo dõi 112M",
                                    color = Color.Gray,
                                    fontSize = 10.sp
                                )
                            }
                        }
                        Button(
                            onClick = { navigator.navigate(ChooseGroupScreenDestination) },
                            modifier = Modifier
                                .weight(4f)
                                .padding(start = 10.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = BackgroundBlue),
                            shape = RoundedCornerShape(40)
                        ) {
                            Text(
                                text = "Xem thêm",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.primary,
                                maxLines = 1
                            )
                        }
                    }
                    if (postList.isEmpty()){
                        Text(text = "Hiện chưa có bài đăng nào ", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
                    }
                    LazyColumn(state = scrollState, modifier = Modifier.padding(bottom=50.dp)
                    ) {
                        if (postList.isEmpty()&&isLoading){
                            items(10) {
                                ShimmerLoading()
                            }
                        }
                        else
                        items(postList.size) {
                            if (it >= postList.size - 1 && !endReached) {
                                postViewModel.loadPostPaginated()
                            }
                            PostEntry(
                                post = postList[it],
                                navigator,
                                postViewModel = postViewModel,
                                avatar = avatar?:""
                            )
                        }
                    }
                }
            }
        }
    }

}
