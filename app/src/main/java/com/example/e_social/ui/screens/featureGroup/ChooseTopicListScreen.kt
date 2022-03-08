package com.example.e_social.ui.screens.featureGroup


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.e_social.R
import com.example.e_social.models.domain.model.TopicIndexListEntry
import com.example.e_social.ui.components.NextStepButton
import com.example.e_social.ui.screens.destinations.MainScreenDestination
import com.example.e_social.ui.theme.Grey100
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun TopicListScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel =hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val sliderPosition =loginViewModel.sliderValue.value
        val steps = loginViewModel.steps.value

        Column {
            Column(modifier = Modifier.weight(1f).fillMaxSize(), horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Choose your favorite topics?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top=10.dp)
                    )
                SliderBar(sliderPosition=sliderPosition,steps=steps,onChange={loginViewModel.onChangSlider(it)})
            }
            Row(modifier = Modifier.weight(1f)) {
                ComposeMenu()
            }
            Row(modifier = Modifier.weight(6f)) {
                Spacer(modifier = Modifier.height(10.dp))
                TopicList(navigator = navigator)
                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(modifier = Modifier
                .weight(1.5f)
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                NextStepButton(
                    onButtonClick = {
                        navigator.navigate(MainScreenDestination)
                    }
                ){
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "Next",
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Outlined.ArrowForward, contentDescription ="ssdsadsa" )
                }
            }
        }
    }
}



@Composable
fun TopicList(
    navigator: DestinationsNavigator,
    viewModel: TopicListViewModel = hiltViewModel()
) {
    val topicList by remember { viewModel.topicList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        val itemCount = if (topicList.size % 3 == 0) {
            topicList.size / 3
        } else {
            topicList.size / 3
        }
        items(itemCount) {
            if (it >= itemCount-2 && !endReached) {
                viewModel.loadTopicPaginated()
            }
            TopicIndexRow(rowIndex = it, entries = topicList, navigator = navigator)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadTopicPaginated()
            }
        }
    }

}

@Composable
fun TopicIndexEntry(
    entry: TopicIndexListEntry,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: TopicListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black),
        startY = sizeImage.height.toFloat() / 1.5f,  // 1/3
        endY = sizeImage.height.toFloat()
    )
    val checkedState = remember { mutableStateOf(false) }
    Box(
        contentAlignment = Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .shadow(elevation = 8.dp, RoundedCornerShape(10.dp))
            .clickable {
//                navigator.navigate(
//
//                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://gaplo.tech/content/images/2020/03/android-jetpack.jpg")
                .crossfade(true).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            modifier = Modifier.onGloballyPositioned {
                sizeImage = it.size
            }
        )
        Box(modifier = Modifier
            .matchParentSize()
            .background(gradient))
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.align(BottomEnd),
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(uncheckedColor = Color.White, checkmarkColor = MaterialTheme.colors.primary, checkedColor = Color.White)
        )
        Text(
            text = entry.pokemonName,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

    }
}

@Composable
fun TopicIndexRow(
    rowIndex: Int,
    entries: List<TopicIndexListEntry>,
    navigator: DestinationsNavigator
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TopicIndexEntry(
                entry = entries[rowIndex * 3],
                navigator = navigator,
                modifier = Modifier.weight(1f)
            )
            if (entries.size >= rowIndex * 3 + 2) {
                TopicIndexEntry(
                    entry = entries[rowIndex * 3 + 1],
                    navigator = navigator,
                    modifier = Modifier.weight(1f)
                )
                TopicIndexEntry(
                    entry = entries[rowIndex * 3 + 2],
                    navigator = navigator,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}



@Composable
fun ComposeMenu(){
    var expanded = remember { mutableStateOf(false) }
    val items = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
    val disabledValue = "B"
    var selectedIndex = remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Curriculum:", textAlign = TextAlign.Center, color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween){
                    Button(
                        modifier=Modifier.width(120.dp),
                        colors=ButtonDefaults.buttonColors(backgroundColor = Grey100),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            expanded.value = true
                        },content = {
                            Text("Level ${items[selectedIndex.value]}")
                            Icon( Icons.Default.ArrowDropDown, contentDescription = null)

                        })
                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Grey100)
                        .shadow(elevation = 2.dp)
                        .width(120.dp),
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex.value = index
                            expanded.value = false
                        }) {
                            Text(text = s.toString() )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun SliderBar(sliderPosition:Float,steps:Float,onChange:(Float)->Unit){

    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)){
        Slider(value = sliderPosition, onValueChange =onChange, valueRange = 1f..2f,steps=2 )
    }
}
