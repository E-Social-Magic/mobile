package com.example.e_social.ui.components.posts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.e_social.R
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.ui.theme.Grey100

@Composable
fun HeaderPost(postEntry: PostEntry){
    Row(modifier = Modifier.background(Grey100).padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = "https://gaplo.tech/content/images/2020/03/android-jetpack.jpg")
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(CircleCropTransformation())
                    }).build()
            ),
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(
                    R.string.post_group_header,
                    "Math",
                    "Group giai toan"

                ),
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = stringResource(
                    R.string.post_user_header,
                    "username",
                    "3 day ago"
                ),
                color = Color.Gray
            )

        }
        MoreActionsMenu()
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
                modifier=Modifier.wrapContentSize().width(200.dp),
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

