package com.example.e_social.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_social.R


@Composable
fun SimpleTopAppBar(title: String, onIconBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .background(color = Color.White)
            .shadow(elevation = 1.dp)
            .padding(horizontal = 16.dp)
            .clickable { }
    ) {
        IconButton(onClick = onIconBackClick) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null
            )
        }
        Text(
            text = title,
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.15.sp
            ),
        )
    }
}