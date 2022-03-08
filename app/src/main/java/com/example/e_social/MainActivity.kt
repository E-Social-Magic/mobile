package com.example.e_social

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.e_social.ui.components.*
import com.example.e_social.ui.screens.*
import com.example.e_social.ui.screens.destinations.*
import com.example.e_social.ui.screens.featureLogin.LoginScreen
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.screens.featureLogin.SignUpScreen
import com.example.e_social.ui.screens.featureProfile.ProfileScreen
import com.example.e_social.ui.theme.EsocialTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel:LoginViewModel= hiltViewModel()
            val isShowBar =loginViewModel.isLogin.value
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val snackBarController = SnackBarController(coroutineScope)
            var navController = rememberNavController()
            EsocialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState },
                        content = {
                            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController) {
                                composable(MainScreenDestination) {
                                    MainScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel= loginViewModel
                                    )
                                }
                                composable(LoginScreenDestination) {
                                    LoginScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel=loginViewModel
                                    )
                                }
                                composable(SignUpScreenDestination) {
                                    SignUpScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel=loginViewModel
                                    )
                                }
                                composable(ProfileScreenDestination) {
                                    ProfileScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel=loginViewModel
                                    )
                                }
                                composable(VideoScreenDestination) {
                                    VideoScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController
                                    )
                                }
                                composable(QuizScreenDestination) {
                                    QuizScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState,
                                        coroutineScope = coroutineScope,
                                        snackBarController = snackBarController,
                                        loginViewModel=loginViewModel
                                    )
                                }
                            }
                        },
                        topBar = {if(isShowBar)TopApp(title="E-Social", icon = Icons.Outlined.Search )
                            {

                            }
                                 },
                        bottomBar = { if(isShowBar) BottomNavController(navController = navController) }
                    )
                }
            }

        }

    }
}