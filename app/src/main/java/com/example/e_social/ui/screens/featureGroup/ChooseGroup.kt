package com.example.e_social.ui.screens.featureGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.R
import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.ui.screens.destinations.ChooseGroupScreenDestination
import com.example.e_social.ui.screens.featurePost.ImageBuilder
import com.example.e_social.ui.screens.featurePost.ImageBuilderCircle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.Job


@Destination
@Composable
fun ChooseGroupScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    viewModel: GroupViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val groups = viewModel.allGroups.value
    var searchedText by remember { mutableStateOf("") }
    var currentJob by remember { mutableStateOf<Job?>(null) }
    val activeColor = MaterialTheme.colors.onSurface
    LaunchedEffect(Unit) {
        viewModel.searchGroups(searchedText)
    }

    Column {
        ChooseGroupTopBar(navigator = navigator)
//        TextField(
//            value = searchedText,
//            onValueChange = {
//                searchedText = it
//                currentJob?.cancel()
//                currentJob = scope.async {
//                    delay(SEARCH_DELAY_MILLIS)
//                    viewModel.searchGroups(searchedText)
//                }
//            },
//            leadingIcon = {
//                Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
//            },
//            label = { Text(stringResource(R.string.search)) },
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = activeColor,
//                focusedLabelColor = activeColor,
//                cursorColor = activeColor,
//                backgroundColor = MaterialTheme.colors.surface
//            )
//        )
        if (groups != null)
            Searchedgroups(navigator = navigator, groups = groups, modifier = modifier){
                viewModel.onSelectedGroup(it)
                resultNavigator.navigateBack(result = it.id)
            }
    }

}

@Composable
fun Searchedgroups(
    navigator: DestinationsNavigator,
    groups: TopicList,
    modifier: Modifier = Modifier,
    onGroupClicked: (Topic) -> Unit,

) {
    Groups(groups = groups, onGroupClicked = onGroupClicked)
}

@Composable
fun ChooseGroupTopBar(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colors
    TopAppBar(
        title = {
            Text(
                fontSize = 20.sp,
                text = "Chọn nhóm",
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navigator.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
        backgroundColor = colors.primary,
        elevation = 0.dp,
        modifier = modifier
            .height(48.dp)
            .background(Color.Blue)
    )
}

@Composable
fun Groups(modifier: Modifier = Modifier, groups: TopicList, onGroupClicked: (Topic) -> Unit) {

    Spacer(modifier = modifier.height(4.dp))

    groups.groups.forEach {
        Group(it, onGroupClicked = onGroupClicked)
    }
}

@Composable
fun Group(group: Topic, modifier: Modifier = Modifier, onGroupClicked: (Topic) -> Unit = {}) {
    Row(modifier = modifier
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        .fillMaxWidth()
        .clickable { onGroupClicked.invoke(group) }
    ) {
        ImageBuilder(30.dp, group.avatar)
        Text(
            fontSize = 16.sp,
            text = group.groupName,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun BackgroundText(text: String) {
    Text(
        fontWeight = FontWeight.Medium,
        text = text,
        fontSize = 10.sp,
        color = Color.DarkGray,
        modifier = Modifier
            .background(color = MaterialTheme.colors.secondary)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth()
    )
}

@Composable
fun GroupPicker(
    navigator: DestinationsNavigator,
    selectedGroup: Topic?,
    resultRecipient:ResultRecipient<ChooseGroupScreenDestination,String>
) {
    val selectedText = selectedGroup?.groupName ?: "Chọn nhóm"
    val avatar = selectedGroup?.avatar ?:  "https://gaplo.tech/content/images/2020/03/android-jetpack.jpg"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 240.dp)
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp)
            .clickable {
                navigator.navigate(ChooseGroupScreenDestination)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageBuilderCircle(size = 35.dp, url =avatar)
        Text(
            text = selectedText,
            modifier = Modifier.padding(start = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}