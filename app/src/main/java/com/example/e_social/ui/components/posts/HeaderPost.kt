package com.example.e_social.ui.components.posts

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.ui.screens.featureProfile.ViewProfileOption
import com.example.e_social.ui.theme.Grey100
import com.example.e_social.util.TimeConverter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderPost(navigator: DestinationsNavigator,userId:String,authorAvatar:String,userName:String,createdAt:String){
    var expand by remember{mutableStateOf(false)}
    Row(modifier = Modifier
        .background(Grey100)
        .padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = authorAvatar)
                    .error(R.drawable.default_avatar)
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(CircleCropTransformation())
                    }).build()
            ),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
                .clickable { expand=true },

        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = userName,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = TimeConverter.converter(createdAt),
                color = Color.Gray,
                fontSize = 12.sp
            )

        }
        ViewProfileOption(navigator = navigator, expanded = expand, onDismiss = { expand=false }, userId = userId)
//        MoreActionsMenu()
    }
}

@Composable
fun MoreActionsMenu(){
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.Gray,
                contentDescription = "more action"
            )
        }
            DropdownMenu(
                modifier= Modifier
                    .wrapContentSize()
                    .width(200.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                CustomDropdownMenuItem(
                    icon = Icons.Outlined.BookmarkBorder,
                    text = "Save"
                )
                CustomDropdownMenuItem(
                    icon = Icons.Outlined.ReportProblem,
                    text = "Report problem"
                )
        }
    }
}
@Composable
fun CustomDropdownMenuItem(
    icon:ImageVector,
    color: Color = Color.Black,
    text: String,
    onClickAction: () -> Unit = {}
) {
    DropdownMenuItem(onClick = onClickAction, modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                tint = color,
                contentDescription =  "some action",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = color)
        }
    }
}

