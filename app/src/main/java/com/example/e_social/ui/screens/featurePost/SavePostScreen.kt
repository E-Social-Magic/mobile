package com.example.e_social.ui.screens.featurePost

import android.Manifest
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.R
import com.example.e_social.util.CameraView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File



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
    if (isCameraOpen){
        CameraView(
            onImageCaptured = { uri, fromGallery ->
            newPostRequest.files= listOf(uri.toFile())
//                newPostRequest.files?.toString()?.let { Log.d("Files", it) }
//                val file = File(uri.path.toString())
//                val file2= File(uri.toString())
//                Log.d("Files", file.absolutePath)
//                Log.d("Files", file2.absolutePath)
            isCameraOpen=false
        }, onError = { imageCaptureException ->
            coroutineScope.launch {
//                    scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
            }
        })
    }
    else
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
                onPostClick = {postViewModel.createPost()}
            )

        },
        content = {
            SavePostContent(title = newPostRequest.title, content = newPostRequest.content,
                onTitleChange = { postViewModel.onTitleChange(it) },
                onContentChange = { postViewModel.onContentChange(it) },
                onCameraClick = {it->isCameraOpen=true}
            )
        }
    )
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SavePostContent(
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onCameraClick:(Boolean)->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )


    Column(modifier = Modifier.fillMaxSize()) {
        ContentTextField(
            label = "Title",
            text = title,
            onTextChange = onTitleChange
        )
        ContentTextField(
            modifier = Modifier
                .heightIn(max = 360.dp, min = 180.dp)
                .padding(top = 16.dp),
            label = "Describe your problems",
            text = content,
            onTextChange = onContentChange
        )
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            Button(
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    onCameraClick.invoke(true)
                }
            ) {
                Text(text = "Permission")
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
    onPostClick:()->Unit

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
