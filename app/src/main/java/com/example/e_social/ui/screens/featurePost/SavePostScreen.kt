package com.example.e_social.ui.screens.featurePost

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.e_social.ui.theme.Grey300
import com.example.e_social.util.CameraView
import com.example.e_social.util.FileUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.launch
import java.io.File


@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavePostScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
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
                newPostRequest.files = listOf(uri.toFile()).plus(newPostRequest.files)
                isCameraOpen = false
            }, onError = { imageCaptureException ->
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
                }
            })
    } else
        Scaffold(
            topBar = {
                val isEditingMode = false
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
                    onContentChange = { postViewModel.onContentChange(it)},
                    onFilesChange= {list: List<File> -> newPostRequest.files = list.plus(newPostRequest.files) },
                    onCameraClick = { it -> isCameraOpen = true }
                )
            }
        )
}


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun SavePostContent(
    title: String,
    content: String,
    files: List<File>,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onCameraClick: (Boolean) -> Unit,
    onFilesChange:(List<File>)->Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val storePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_MEDIA_LOCATION
    )
    val context = LocalContext.current
    val countGrid = if (files.size <= 2) 1 else 2
    val fee = listOf(SavePostOption(index=1,Icons.Default.FreeBreakfast,"Miễn phí"),SavePostOption(index=2,Icons.Default.Money,"Tính phí"))
    val hideName = listOf(SavePostOption(index=1,Icons.Default.Public,"Hiển thị tên"),SavePostOption(index=2,Icons.Default.PrivacyTip,"Ẩn tên"))
    val time = listOf(SavePostOption(index=1,Icons.Default.FreeBreakfast,"Hiển thị tên"),SavePostOption(index=2,Icons.Default.FreeBreakfast,"Ẩn tên"))

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(bottom = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                DropDown(
                    items=fee,
                    modifier = Modifier.padding(15.dp)
                ) {
                }
                DropDown(
                    items=hideName,
                    modifier = Modifier.padding(15.dp)
                ) {
                }
                DropDown(
                    items=time,
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
                modifier = Modifier.heightIn(max = 600.dp),
                label = "Describe your problems",
                text = content,
                onTextChange = onContentChange
            )
            LazyVerticalGrid(cells = GridCells.Fixed(countGrid)) {
                if (files.size <= 2) items(files.size) { index ->
                    ImageBuilder(480.dp, file = files[index])
                }
                else
                    if (files.size < 5)
                        items(files.size) { index ->
                            ImageBuilder(240.dp, file = files[index])
                        }
                    else {
                        items(4) { index ->
                            if (index == 3) {
                                    Box(contentAlignment = Alignment.Center){
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .crossfade(true)
                                                    .data(data = files[index])
                                                    .build()
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(240.dp)
                                                .padding(4.dp)
                                                .background(color = Color.Black),
                                            alpha = 0.6F,
                                            contentScale = ContentScale.Crop,
                                        )
                                        Text(
                                            text = "+ ${files.size-4}",
                                            fontSize = 28.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    }
                            } else
                                ImageBuilder(240.dp, file = files[index])

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
                    storePermissionState.launchPermissionRequest()
                    TedImagePicker.with(context)
                        .startMultiImage { uriList ->
                            uriList.map{ uri->FileUtils.getFile(context = context,uri) }
                                .let { onFilesChange.invoke(it as List<File>) }
                        }
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

@OptIn(ExperimentalPermissionsApi::class)
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
    items:List<SavePostOption>,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
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
            Icon(
                imageVector = items[selectedIndex.value].icon,
                contentDescription =null ,
                tint = Color.Gray,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "${items[selectedIndex.value].text}",
                fontSize = 14.sp,
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
                .width(140.dp)
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
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = value.icon,
                            contentDescription =null ,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = value.text, textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }
}
@Composable
fun ImageBuilder(size: Dp, file: File){
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(data = file)
                .build()
        ),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .padding(4.dp),
        contentScale = ContentScale.Crop,
    )
}
data class SavePostOption(
    val index:Int,
    val icon:ImageVector,
    val text:String,

)