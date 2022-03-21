package com.example.e_social.ui.screens.featureProfile

//import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController,
    loginViewModel: LoginViewModel
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    var tenbien = "string"
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .shadow(elevation = 2.dp)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Profile",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.15.sp
                ),
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Đăng xuất")
            }

        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(4.dp))
            ProfileSection()
            Spacer(modifier = Modifier.height(25.dp))
            Spacer(modifier = Modifier.height(25.dp))
            Spacer(modifier = Modifier.height(10.dp))
            PostTabView(
                imageWithTexts = listOf(
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_grid),
                        text = "Posts"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_reels),
                        text = "Reels"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_igtv),
                        text = "IGTV"
                    ),
                )
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> PostSection(
                    posts = listOf(
                        painterResource(id = R.drawable.unsplash),
                        painterResource(id = R.drawable.unsplash1),
                        painterResource(id = R.drawable.unsplash2),
                        painterResource(id = R.drawable.unsplash3),
                        painterResource(id = R.drawable.unsplash4),
                        painterResource(id = R.drawable.unsplash5),
                        painterResource(id = R.drawable.unsplash6),
                        painterResource(id = R.drawable.unsplash8),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RoundImage(
                image = rememberImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://gaplo.tech/content/images/2020/03/android-jetpack.jpg")
                        .error(R.drawable.default_avatar)
                        .apply(block = fun ImageRequest.Builder.() {
                            transformations(CircleCropTransformation())
                        }).build()
                ),
                modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection()
        }
        HorizontalDivider()
        ProfileDescription(
            displayName = "Programming Mentor",
            description = "3 years of coding experience\n" +
                    "Want me to make your app? Send me an email!\n" +
                    "Follow to my Github !",
            url = "https://github.com/longnguyen-2k",
            followedBy = listOf("codinginflow", "miakhalifa"),
            otherCount = 17
        )
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        ProfileHeader()
        HorizontalDivider()
        Row(
            modifier = Modifier.height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalDivider()
                    Row(Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable { }
                        .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileStat(numberText = "601", text = "Posts")
                    }

                    VerticalDivider()
                    Row(Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable { }
                        .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileStat(numberText = "100K", text = "Followers")
                    }
                    VerticalDivider()
                    Row(Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable { }
                        .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileStat(numberText = "72", text = "Following")

                    }
                }
        }

    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(Modifier.fillMaxWidth().padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = numberText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = text, color = Color.Gray, fontSize = 12.sp)
    }

}

@Composable
fun ProfileDescription(
    displayName: String,
    description: String,
    url: String,
    followedBy: List<String>,
    otherCount: Int
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = url,
            color = Color(0xFF3D3D91),
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        if (followedBy.isNotEmpty()) {
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    append("Followed by ")
                    followedBy.forEachIndexed { index, name ->
                        pushStyle(boldStyle)
                        append(name)
                        pop()
                        if (index < followedBy.size - 1) {
                            append(", ")
                        }
                    }
                    if (otherCount > 2) {
                        append(" and ")
                        pushStyle(boldStyle)
                        append("$otherCount others")
                    }
                },
                letterSpacing = letterSpacing,
                lineHeight = lineHeight
            )
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(6.dp)
    ) {
        if (text != null) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithTexts: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val inactiveColor = Color(0xFF777777)
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        imageWithTexts.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color.Black,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = item.image,
                    contentDescription = item.text,
                    tint = if (selectedTabIndex == index) Color.Black else inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostSection(
    posts: List<Painter>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = modifier
            .scale(1.01f)
            .padding(bottom = 80.dp)
    ) {
        items(posts.size) {
            Image(
                painter = posts[it],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
            )
        }
    }
}

data class ImageWithText(
    val image: Painter,
    val text: String
)