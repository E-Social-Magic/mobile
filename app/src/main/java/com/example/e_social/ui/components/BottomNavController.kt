package com.example.e_social.ui.components

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.e_social.R
import com.example.e_social.ui.screens.destinations.*
import com.example.e_social.ui.screens.navDestination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val contentDescriptionResourceId: Int
) {
    Home(
        PostScreenDestination,
        icon = Icons.Outlined.Home,
        contentDescriptionResourceId = R.string.home
    ),
    Quiz(
        QuizScreenDestination as DirectionDestinationSpec,
        icon = Icons.Outlined.QuestionAnswer,
        contentDescriptionResourceId = R.string.quiz
    ),
    Video(
        VideosScreenDestination,
        icon = Icons.Outlined.VideoLibrary,
        contentDescriptionResourceId = R.string.video
    ),
    Account(
        ProfileScreenDestination,
        icon = Icons.Outlined.AccountBox,
        contentDescriptionResourceId = R.string.profile
    )

}

@Composable
fun BottomNavController(modifier: Modifier = Modifier, navController: NavController) {
    val currentDestination: Destination? =
        navController.currentBackStackEntryAsState()
            .value?.navDestination
    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.contentDescriptionResourceId)
                    )
                },
                label = { Text(stringResource(destination.contentDescriptionResourceId)) },
            )
        }

    }

}

