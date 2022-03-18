package com.example.e_social

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TopApp
import com.example.e_social.ui.screens.MainScreen
import com.example.e_social.ui.screens.NavGraphs
import com.example.e_social.ui.screens.QuizScreen
import com.example.e_social.ui.screens.destinations.*
import com.example.e_social.ui.screens.featureGroup.TopicListScreen
import com.example.e_social.ui.screens.featureLogin.LoginScreen
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.screens.featureLogin.SignUpScreen
import com.example.e_social.ui.screens.featurePost.PostScreen
import com.example.e_social.ui.screens.featurePost.PostViewModel
import com.example.e_social.ui.screens.featurePost.SavePostScreen
import com.example.e_social.ui.screens.featureProfile.ProfileScreen
import com.example.e_social.ui.screens.featureVideo.VideosScreen
import com.example.e_social.ui.theme.EsocialTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val isShowBar = loginViewModel.isShowBar.value
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val snackBarController = SnackBarController(coroutineScope)
            var navController = rememberNavController()

            EsocialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState },
                        content = {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = navController
                            ) {
                                composable(MainScreenDestination) {
                                    MainScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel = loginViewModel,
                                        changeBarState= {bar->loginViewModel.isShowBar.value=bar}
                                    )
                                }
                                composable(PostScreenDestination) {
                                    PostScreen(
                                        navigator = destinationsNavigator,
                                        loginViewModel = loginViewModel,
                                        changeBarState= {bar->loginViewModel.isShowBar.value=bar}
                                    )
                                }
                                composable(LoginScreenDestination) {
                                    LoginScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel = loginViewModel
                                    )
                                }
                                composable(SavePostScreenDestination){
                                    SavePostScreen(navigator = destinationsNavigator, scaffoldState =scaffoldState )
                                }
                                composable(SignUpScreenDestination) {
                                    SignUpScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel = loginViewModel
                                    )
                                }
                                composable(ProfileScreenDestination) {
                                    ProfileScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel = loginViewModel
                                    )
                                }
                                composable(VideosScreenDestination) {
                                    VideosScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                    )
                                }
                                composable(QuizScreenDestination) {
                                    QuizScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel = loginViewModel
                                    )
                                }
                                composable(TopicListScreenDestination) {
                                    TopicListScreen(
                                        navigator = destinationsNavigator,
                                        loginViewModel = loginViewModel
                                    )
                                }
                            }
                        },
                        topBar = {
                            if (isShowBar) TopApp(
                                title = "E-Social",
                                icon = Icons.Outlined.Search
                            )
                            {}
                        },
                        bottomBar = { if (isShowBar) BottomNavController(navController = navController) },
                    )
                }
            }
        }
    }
     fun getDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}



