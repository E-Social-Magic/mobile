package com.example.e_social.router

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.e_social.R

sealed class Router(val stringResourceId: Int) {
    object Home : Router(R.string.home)
    object Quiz : Router(R.string.quiz)
    object Video : Router(R.string.video)
    object Profile : Router(R.string.profile)
    object Login : Router(R.string.login)
    object SignUp : Router(R.string.signup)
    object ForgotPassword : Router(R.string.forgotPassword)
}

    object AppRouter {
        var currentScreen: MutableState<Router> = mutableStateOf(
            Router.Home
        )

        private var previousScreen: MutableState<Router> = mutableStateOf(
            Router.Home
        )

//        fun navigateTo(destination: Screen) {
//            previousScreen.value = currentScreen.value
//            currentScreen.value = destination
//        }

        fun goBack() {
            currentScreen.value = previousScreen.value
        }

}
