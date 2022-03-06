package com.example.e_social.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.featureGroup.TopicListScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController,
) {
    PostScreen()
}








