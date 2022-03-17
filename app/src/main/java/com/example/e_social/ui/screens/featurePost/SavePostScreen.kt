package com.example.e_social.ui.screens.featurePost

import android.Manifest
import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.e_social.util.CameraView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.launch
import java.io.File
import com.example.e_social.MainActivity


@Destination
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavePostScreen(
    navigator: DestinationsNavigator,
    postViewModel: PostViewModel = hiltViewModel()
) {
    val bottomDrawerState: BottomDrawerState =
        rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val moveNoteToTrashDialogShownState: MutableState<Boolean> = rememberSaveable {
        mutableStateOf(false)
    }
    val newPostRequest = postViewModel.newPost.value
    var isCameraOpen by remember {
        mutableStateOf(false)
    }
    if (isCameraOpen) {
        CameraView(
            onImageCaptured = { uri, fromGallery ->
                newPostRequest.files = listOf(uri.toFile())
                isCameraOpen = false
            }, onError = { imageCaptureException ->
                coroutineScope.launch {
//                    scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
                }
            })
    } else
        Scaffold(
            topBar = {
                val isEditingMode: Boolean = false
                SavePostTopAppBar(
                    isEditingMode = isEditingMode,
                    onBackClick = { navigator.navigateUp() },
                    onSaveNoteClick = { },
                    onOpenColorPickerClick = {
                        coroutineScope.launch { bottomDrawerState.open() }
                    },
                    onDeleteNoteClick = { moveNoteToTrashDialogShownState.value = true },
                    onPostClick = { postViewModel.createPost() }
                )

            },
            content = {
                SavePostContent(title = newPostRequest.title,
                    content = newPostRequest.content,
                    files = newPostRequest.files,
                    onTitleChange = { postViewModel.onTitleChange(it) },
                    onContentChange = { postViewModel.onContentChange(it) }
                ) { it -> isCameraOpen = true }
            }
        )
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SavePostContent(
    title: String,
    content: String,
    files: List<File>?,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onCameraClick: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                DropDown(
                    text = "Free",
                    modifier = Modifier.padding(15.dp)
                ) {
                }
                DropDown(
                    text = "Free",
                    modifier = Modifier.padding(15.dp)
                ) {
                }
                DropDown(
                    text = "Free",
                    modifier = Modifier.padding(15.dp)
                ) {
                }
            }
            ContentTextField(
                label = "Title",
                text = title,
                onTextChange = onTitleChange
            )
            ContentTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                label = "Describe your problems",
                text = content,
                onTextChange = onContentChange
            )
            if (files != null){
                LazyColumn(Modifier.fillMaxSize()) {
                    items(files.size) { index ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = files[index])
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    onCameraClick.invoke(true)
                }
            ) {
                Icon(
                    Icons.Filled.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color(0xFF0033AA)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    onCameraClick.invoke(true)
                    TedImagePicker.with(context)
                        .startMultiImage { uriList ->  }
                }
            ) {
                Icon(
                    Icons.Filled.PhotoLibrary,
                    contentDescription = "PhotoLib",
                    tint = Color(0xFF4CBB17),
                )
            }
        }
    }
}

@Composable
private fun SavePostTopAppBar(
    isEditingMode: Boolean,
    onBackClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onOpenColorPickerClick: () -> Unit,
    onDeleteNoteClick: () -> Unit,
    onPostClick: () -> Unit

) {
    TopAppBar(
        backgroundColor = Color.White,
        title = { Text(text = "Create Post") },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Create Post Button",
                    tint = Color.Black
                )

            }
        },
        actions = {
            Button(
                onClick = onPostClick,
                modifier = Modifier.background(MaterialTheme.colors.background),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Post", fontWeight = FontWeight.ExtraBold)
            }
        })
}

@Composable
private fun CheckedOption(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        Modifier
            .padding(8.dp)
            .padding(16.dp)
    ) {
        Text(text = "Check off?", modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun ContentTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
    )
}


@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val items = listOf("Miễn Phí", "Tính Phí")
    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    var selectedIndex = remember { mutableStateOf(0) }

    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    isOpen = !isOpen
                }
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Gray
                )
                .padding(8.dp)
        ) {
            Text(
                text = "${items[selectedIndex.value]}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.Gray,
                modifier = Modifier
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = { isOpen = false },
            modifier = Modifier
                .shadow(elevation = 2.dp)
                .width(120.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value),
        ) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    isOpen = false
                }) {
                    Text(text = value, textAlign = TextAlign.Center)
                }
            }
        }
    }
}