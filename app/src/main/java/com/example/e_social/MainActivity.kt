package com.example.e_social

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.e_social.ui.components.BottomNavController
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TopApp
import com.example.e_social.ui.screens.NavGraphs
import com.example.e_social.ui.screens.QuizScreen
import com.example.e_social.ui.screens.destinations.*
import com.example.e_social.ui.screens.featureGroup.TopicListScreen
import com.example.e_social.ui.screens.featureLogin.LoginScreen
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.screens.featureLogin.SignUpScreen
import com.example.e_social.ui.screens.featurePost.PostScreen
import com.example.e_social.ui.screens.featurePost.SavePostScreen
import com.example.e_social.ui.screens.featureProfile.ProfileScreen
import com.example.e_social.ui.screens.featureVideo.VideosScreen
import com.example.e_social.ui.theme.EsocialTheme
import com.example.e_social.util.SessionManager
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.ChatDomain
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val isShowTopBar = loginViewModel.isShowTopBar.value
            val isShowBottomBar = loginViewModel.isShowBottomBar.value
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val snackBarController = SnackBarController(coroutineScope)
            var navController = rememberNavController()
            LaunchedEffect(key1 = loginViewModel.isLogin.value) {
                val user = loginViewModel.user.value
                if (user != null) {
                    val client = ChatClient.Builder(apiKey = "qx5us2v6xvmh", applicationContext).build()
                    ChatDomain.Builder(client, appContext = applicationContext).build()
                    val userChat = User().apply {
                        id = "jc"
                        name = "Jc Miñarro"
                        image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128"
                    }
                    client.connectUser(user = userChat, token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiamMifQ.2_5Hae3LKjVSfA0gQxXlZn54Bq6xDlhjPx2J7azUNB4d")
                        .enqueue()
                }
            }


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
                                composable(PostScreenDestination) {
                                    PostScreen(
                                        navigator = destinationsNavigator,
                                        loginViewModel = loginViewModel,
                                        changeBarState = { bar ->
                                            loginViewModel.isShowBottomBar.value = bar
                                            loginViewModel.isShowTopBar.value = bar
                                        }
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
                                composable(SavePostScreenDestination) {
                                    SavePostScreen(
                                        navigator = destinationsNavigator,
                                        scaffoldState = scaffoldState
                                    )
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
                                        loginViewModel = loginViewModel,
                                        changeBarState = { bar ->
                                            loginViewModel.isShowTopBar.value = bar
                                        },
                                        onItemClick={channel ->  startActivity(MessagesActivity.createIntent(this@MainActivity, channelId = channel.cid))},
                                        onBackPressed = {finish()}
                                    )
                                }
                                composable(TopicListScreenDestination) {
                                    TopicListScreen(
                                        navigator = destinationsNavigator,
                                        loginViewModel = loginViewModel,
                                    )
                                }
                            }
                        },
                        topBar = {
                            if (isShowTopBar) TopApp(
                                title = "E-Social",
                                icon = Icons.Outlined.Search
                            )
                            {}
                        },
                        bottomBar = { if (isShowBottomBar) BottomNavController(navController = navController) },
                    )
                }
            }
        }
    }

}



