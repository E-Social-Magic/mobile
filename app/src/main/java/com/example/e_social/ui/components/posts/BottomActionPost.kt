package com.example.e_social.ui.components.posts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_social.R
@Composable
fun BottomPostAction(){
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VotingAction(text = "109", onUpVoteAction = {}, onDownVoteAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_comment_24,
            text = "20",
            onClickAction = {}
        )
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_share_24,
            text = stringResource(R.string.share),
            onClickAction = {}
        )

    }
}
@Composable
fun VotingAction(
    text: String,
    onUpVoteAction: () -> Unit,
    onDownVoteAction: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ArrowButton(onUpVoteAction, R.drawable.ic_baseline_arrow_upward_24)
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        ArrowButton(onDownVoteAction, R.drawable.ic_baseline_arrow_downward_24)
    }

}

@Composable
fun ArrowButton(onClickAction: () -> Unit, arrowResourceId: Int) {
    IconButton(onClick = onClickAction, modifier = Modifier.size(30.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(arrowResourceId),
            contentDescription = stringResource(id = R.string.upvote),
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}
@Composable
fun PostAction(
    @DrawableRes vectorResourceId: Int,
    text: String,
    onClickAction: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClickAction)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                ImageVector.vectorResource(id = vectorResourceId),
                contentDescription = stringResource(id = R.string.post_action),
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = Color.Gray, fontSize = 14.sp)
        }
    }
}
