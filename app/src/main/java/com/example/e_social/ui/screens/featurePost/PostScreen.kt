package com.example.e_social.ui.screens.featurePost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.posts.PostEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PostScreen(navigator: DestinationsNavigator,postViewModel: PostViewModel= hiltViewModel()){
    val postList by remember { postViewModel.postList }
    val endReached by remember { postViewModel.endReached }
    val loadError by remember { postViewModel.loadError }
    val isLoading by remember { postViewModel.isLoading }
  Surface(modifier = Modifier.fillMaxSize()) {
      LazyColumn{
          items(postList.size){
              if (it >= postList.size-1 && !endReached) {
                  postViewModel.loadPostPaginated()
              }
              PostEntry(post=postList[it],navigator,postViewModel=postViewModel)
          }
      }
  }

}