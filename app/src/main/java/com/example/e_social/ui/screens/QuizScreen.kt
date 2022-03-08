package com.example.e_social.ui.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@Destination
@Composable
fun QuizScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController,
    loginViewModel: LoginViewModel
) {
    Text(text = "This is Quiz screen")
}