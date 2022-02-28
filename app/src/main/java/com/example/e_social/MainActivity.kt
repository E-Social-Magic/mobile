package com.example.e_social

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavGraphNavigator
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.loginFeature.LoginScreen
import com.example.e_social.ui.screens.NavGraphs
import com.example.e_social.ui.screens.destinations.LoginScreenDestination
import com.example.e_social.ui.screens.destinations.SignUpScreenDestination
import com.example.e_social.ui.screens.loginFeature.SignUpScreen
import com.example.e_social.ui.theme.EsocialTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.NavGraphSpec
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val snackBarController = SnackBarController(coroutineScope)
            EsocialTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(scaffoldState = scaffoldState, snackbarHost = {scaffoldState.snackbarHostState},
                content = {
                    Surface(color = MaterialTheme.colors.background) {
                        DestinationsNavHost(navGraph = NavGraphs.root) {
                            composable(LoginScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                                LoginScreen(
                                    navigator = destinationsNavigator, // destinationsNavigator is a `DestinationsNavigator` (also lazily evaluated)
                                    scaffoldState = scaffoldState,
                                    coroutineScope = coroutineScope,
                                    snackBarController = snackBarController
                                )

                            }
                            composable(SignUpScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                                SignUpScreen(
                                    navigator = destinationsNavigator, // destinationsNavigator is a `DestinationsNavigator` (also lazily evaluated)
                                    scaffoldState = scaffoldState,
                                    coroutineScope = coroutineScope,
                                    snackBarController=snackBarController
                                )
                            }
                        }
                    }
                })

            }
        }
    }
}

