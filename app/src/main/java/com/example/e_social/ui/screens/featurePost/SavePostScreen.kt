package com.example.e_social.ui.screens.featurePost

//import coil.compose.rememberAsyncImagePainter
import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.domain.model.PostModel
import com.example.e_social.ui.screens.destinations.ChooseGroupScreenDestination
import com.example.e_social.ui.screens.destinations.PostScreenDestination
import com.example.e_social.ui.screens.featureGroup.GroupPicker
import com.example.e_social.ui.screens.featureGroup.GroupViewModel
import com.example.e_social.util.CameraView
import com.example.e_social.util.FileUtils
import com.example.e_social.util.TimeConverter.getCurrentTime
import com.example.e_social.util.TimeConverter.getLongOfTime
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.launch
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat


@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavePostScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<ChooseGroupScreenDestination, String?>,
    scaffoldState: ScaffoldState,
    postViewModel: PostViewModel = hiltViewModel(),
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var postModel = postViewModel.newPost.value
    val coins = postViewModel.coins
    val selectedGroup = groupViewModel.selectedGroup.value
    var isCameraOpen by remember {
        mutableStateOf(false)
    }
    resultRecipient.onResult {
        if(it != null){
            groupViewModel.onSelectedGroupId(it)
            postViewModel.onGroupSelected(it)
        }
    }

    if (isCameraOpen) {
        CameraView(
            onImageCaptured = { uri, fromGallery ->
                postViewModel.onPostChange(
                    postModel.copy(
                        files = listOf(uri.toFile()).plus(
                            postModel.files!!.map { it })
                    )
                )
                isCameraOpen = false
            }, onError = { imageCaptureException ->
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("An error occurred while trying to take a picture")
                }
            })
    } else
        Scaffold(
            topBar = {
                SavePostTopAppBar(
                    onBackClick = { navigator.navigateUp() },
                    onPostClick = {
                        var check = true
                        if (selectedGroup == null) {
                            check=false
                            Toast.makeText(
                                context,
                                "Vui lòng chọn nhóm để đăng bài",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (postModel.title.isEmpty() ) {
                            check=false
                            Toast.makeText(
                                context,
                                "Vui lòng nhập tiêu đề",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (postModel.content.isEmpty()) {
                            check=false
                            Toast.makeText(
                                context,
                                "Vui lòng nhập nội dung",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (postModel.coins<100 && postModel.costs) {
                            check=false
                            Toast.makeText(
                                context,
                                "Vui lòng nhập số tiền lớn hơn 100 coins",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (check){
                            postViewModel.createPost()
                            navigator.navigate(PostScreenDestination)
                        }
                    }
                )

            },
            content = {
                SavePostContent(
                    navigator = navigator,
                    postModel = postModel,
                    coins =coins,
                    onTitleChange = { postViewModel.onTitleChange(it) },
                    onContentChange = { postViewModel.onContentChange(it) },
                    onFilesChange = { list: List<File> ->
                        postModel.files = list.plus(postModel.files!!.map { it })
                    },
                    selectedGroup = selectedGroup,
                    resultRecipient = resultRecipient,
                    onCameraClick = { it -> isCameraOpen = true },
                    onCostSelected = { postViewModel.onCostSelected(it) },
                    onHideNameSelected = { postViewModel.onHideNameSelected(it) },
                    onExpiredChange = { postViewModel.onExpiredChange(it) },
                    onCoinChange = { postViewModel.onCoinChange(it) }
                )
            }
        )
}


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun SavePostContent(
    navigator: DestinationsNavigator,
    postModel: PostModel,
    selectedGroup: Topic?,
    coins :Long,
    resultRecipient: ResultRecipient<ChooseGroupScreenDestination, String?>,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onCameraClick: (Boolean) -> Unit,
    onFilesChange: (List<File>) -> Unit,
    onHideNameSelected: (Boolean) -> Unit,
    onExpiredChange: (Long) -> Unit,
    onCoinChange: (Int) -> Unit,
    onCostSelected: (Boolean) -> Unit
) {
    val formatter: NumberFormat = DecimalFormat("#,###")
    val dialogDateState = rememberMaterialDialogState()
    val dialogTimeState = rememberMaterialDialogState()

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val storePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_MEDIA_LOCATION
    )
    var expired by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val countGrid = if (postModel.files?.size ?: 0 <= 2) 1 else 2
    val fee = listOf(
        SavePostOption(
            index = 1,
            "Miễn phí",
            value = false
        ),
        SavePostOption(
            index = 2,

            "Tính phí",
            value = true
        )
    )
    val hideName = listOf(
        SavePostOption(
            index = 1,

            "Hiển thị tên",
            value = false
        ),
        SavePostOption(index = 2, "Ẩn tên", value = true)
    )
    val times = listOf(
        SavePostOption(
            index = 1,
            "Vô thời hạn",
            value = false
        ),
        SavePostOption(index = 2, "Đặt thời gian", value = true, getCurrentTime()),
    )


    val fileSize = postModel.files?.size ?: 0
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(bottom = 50.dp)
        ) {
            GroupPicker(navigator, selectedGroup, resultRecipient)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    MaterialDialog(
                        dialogState = dialogDateState,
                        buttons = {
                            positiveButton("Ok")
                            negativeButton("Cancel")
                        }
                    ) {
                        datepicker { date ->
                            dialogTimeState.show()
                            expired = date.toString()
                        }

                    }
                    MaterialDialog(
                        dialogState = dialogTimeState,
                        buttons = {
                            positiveButton("Ok")
                            negativeButton("Cancel")
                        }
                    ) {
                        timepicker { time ->
                            expired += " $time"
                            onExpiredChange.invoke(getLongOfTime(expired))
                        }
                    }
                }
                DropDown(
                    items = fee,
                    modifier = Modifier.padding(15.dp)
                ) {
                    onCostSelected.invoke(it)
                }
                DropDown(
                    items = hideName,
                    modifier = Modifier.padding(15.dp)
                ) {
                    onHideNameSelected.invoke(it)
                }
                DropDown(
                    items = times,
                    modifier = Modifier.padding(15.dp)
                ) {
                    if (it) dialogDateState.show()
                    else {
                        onExpiredChange(0L)
                        expired = ""
                    }
                }
            }
            ContentTextField(
                label = "Tiêu đề",
                text = postModel.title,
                onTextChange = onTitleChange
            )
            ContentTextField(
                modifier = Modifier.heightIn(max = 600.dp),
                label = "Miêu tả nội dung của bạn ở đây",
                text = postModel.content,
                onTextChange = onContentChange
            )
            if (expired.isNotEmpty()) {
                TextField(
                    value = expired,
                    onValueChange = {
                    },
                    placeholder = { Text(text = "Thời gian hết hạn bài đăng") },
                    label = { Text(text = "Thời gian hết hạn bài đăng") },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )
            }
            if (postModel.costs) {
                TextField(
                    value = formatter.format(postModel.coins),
                    onValueChange = {
                        val number = it.replace(",", "").replace(".", "").toIntOrNull()
                        if (number != null && number < coins )
                            onCoinChange.invoke(number)
                    },
                    placeholder = { Text(text = "Nhập phí bạn muôn trả") },
                    label = { Text(text = "Nhập phí bạn muôn trả - số dư còn lại $coins coins") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )
            }
            LazyVerticalGrid(cells = GridCells.Fixed(countGrid)) {
                if (fileSize <= 2) items(fileSize) { index ->
                    ImageBuilder(Int.MAX_VALUE.dp, file = postModel.files?.get(index))
                }
                else
                    if (fileSize < 5)
                        items(fileSize) { index ->
                            ImageBuilder(240.dp, file = postModel.files?.get(index))
                        }
                    else {
                        items(4) { index ->
                            if (index == 3) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = rememberImagePainter(
                                            ImageRequest.Builder(LocalContext.current)
                                                .crossfade(true)
                                                .data(data = postModel.files?.get(index))
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
                                        text = "+ ${fileSize - 4}",
                                        fontSize = 28.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }
                            } else
                                ImageBuilder(240.dp, file = postModel.files?.get(index))

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
                            uriList.map { uri -> FileUtils.getFile(context = context, uri) }
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
    onBackClick: () -> Unit,
    onPostClick: () -> Unit

) {

    TopAppBar(
        backgroundColor = Color.White,
        title = { Text(text = "Đăng câu hỏi") },
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
                Text(text = "Đăng", fontWeight = FontWeight.ExtraBold)
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
    items: List<SavePostOption>,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    action: (Boolean) -> Unit
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
            Text(
                text = items[selectedIndex.value].text,
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
                .width(180.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value),
        ) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    action.invoke(items[selectedIndex.value].value)
                    isOpen = false
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = value.text, textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }
}

@Composable
fun ImageBuilder(size: Dp, file: File?) {
    if (file != null)
        Image(
            painter = rememberImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(data = file)
                    .size(Int.MAX_VALUE)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .padding(4.dp),
            contentScale = ContentScale.Crop,
        )
}

@Composable
fun ImageBuilder(size: Dp, url: String) {
    Image(
        painter = rememberImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(data = url)
                .size(Int.MAX_VALUE)
                .build()
        ),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .padding(4.dp),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun ImageBuilderCircle(size: Dp, url: String) {
    Image(
        painter = rememberImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(data = url)
                .size(Int.MAX_VALUE)
                .transformations(CircleCropTransformation())
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
    val index: Int,
    val text: String,
    val value: Boolean,
    val expired: Long? = 0
)