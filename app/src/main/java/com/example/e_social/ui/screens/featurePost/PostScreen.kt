package com.example.e_social.ui.screens.featurePost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.TopApp
import com.example.e_social.ui.components.posts.PostEntry
import com.example.e_social.ui.screens.destinations.LoginScreenDestination
import com.example.e_social.ui.screens.destinations.SavePostScreenDestination
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator,
    changeBarState: (Boolean) -> Unit,
    postViewModel: PostViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel
) {
    val postList by remember { postViewModel.postList }
    val endReached by remember { postViewModel.endReached }
    val loadError by remember { postViewModel.loadError }
    val isLoading by remember { postViewModel.isLoading }
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
                title = "E-Social",
                icon = Icons.Outlined.Search
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
            LazyColumn {
                items(postList.size) {
                    if (it >= postList.size - 1 && !endReached) {
                        postViewModel.loadPostPaginated()
                    }
                    PostEntry(post = postList[it], navigator, postViewModel = postViewModel)
                }
            }
        }


    }

}