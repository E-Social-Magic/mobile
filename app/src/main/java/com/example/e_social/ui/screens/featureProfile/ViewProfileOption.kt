package com.example.e_social.ui.screens.featureProfile

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.MessagesActivity
import com.example.e_social.ui.components.posts.CustomDropdownMenuItem
import com.example.e_social.ui.screens.destinations.ProfileOtherUserScreenDestination
import com.example.e_social.ui.screens.destinations.ProfileScreenDestination
import com.example.e_social.ui.screens.featureChat.ChatViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ViewProfileOption(navigator: DestinationsNavigator,expanded:Boolean,onDismiss:()->Unit,userId:String){
    val chatViewModel:ChatViewModel= hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    DropdownMenu(
        modifier= Modifier
            .wrapContentSize()
            .width(200.dp),
        expanded = expanded,
        onDismissRequest =onDismiss,
    ) {
        CustomDropdownMenuItem(
            icon = Icons.Outlined.ChatBubbleOutline,
            text = "Chat"
        ){
            coroutineScope.launch {
                chatViewModel.createNewChannel(
                    userId,
                    action = { cid ->
                        context.startActivity(
                            MessagesActivity.createIntent(
                                context,
                                channelId = cid
                            )
                        )
                    })
            }
        }
        CustomDropdownMenuItem(
            icon = Icons.Outlined.AccountBox,
            text = "Xem trang cá nhân "
        ){
            if (chatViewModel.ownerId.value==userId)
                navigator.navigate(ProfileScreenDestination)
            else
                navigator.navigate(ProfileOtherUserScreenDestination(userId))
        }
    }
}