package com.example.e_social.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.posts.PostComponent
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun PostScreen(){
  Box(modifier = Modifier.wrapContentSize()) {

      LazyColumn{
          items(10){ post ->
              PostComponent()
          }
      }
  }

}