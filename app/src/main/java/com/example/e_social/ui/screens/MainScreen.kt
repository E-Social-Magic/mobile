package com.example.e_social.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.destinations.LoginScreenDestination
import com.example.e_social.ui.screens.featureGroup.TopicListScreen
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.screens.featurePost.PostScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@Destination(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = loginViewModel.isLogin.value ){
        if(!loginViewModel.isLogin.value){
            navigator.navigate(LoginScreenDestination)
        }
    }
    PostScreen(navigator =navigator)
}








