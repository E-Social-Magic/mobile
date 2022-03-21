package com.example.e_social.ui.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.destinations.ListChatUserDestination
import com.example.e_social.ui.screens.featureChat.ChatViewModel
import com.example.e_social.ui.screens.featureChat.ListChatUser
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.util.SessionManager
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.offline.ChatDomain
import kotlinx.coroutines.CoroutineScope
import java.util.*
import javax.inject.Inject

@Destination
@Composable
fun ChatScreen (
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel,
    changeBarState:(Boolean)->Unit,
    onItemClick:(Channel)->Unit,
    onBackPressed:()->Unit,
    chatViewModel: ChatViewModel= hiltViewModel()
) {

    LaunchedEffect(key1 = true ){
        changeBarState.invoke(false)
    }
    ChatTheme{
        ChannelsScreen(
            title = "Chat screen",
            isShowingSearch = true,
            isShowingHeader = true,
            onItemClick = onItemClick,
            onBackPressed =onBackPressed,
            onHeaderActionClick = {
                    navigator.navigate(ListChatUserDestination)
            }
            )
    }
}