package ru.hse.app.hasslspace.ui.components.common.card

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun TextChannelCard(
    name: String,
    modifier: Modifier = Modifier,
    onShortClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(43.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .combinedClickable(
                onClick = onShortClick,
                onLongClick = onLongClick
            )
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.hashtag),
            contentDescription = "hashtag",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(13.dp)
        )

        Spacer(Modifier.width(15.dp))

        VariableLight(
            text = name,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun TextChannelCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            TextChannelCard(
                name = "Сервер друзей",
                onShortClick = { println("короткий") },
                onLongClick = { println("длинный") }
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun TextChannelCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextChannelCard(
                name = "Сервер друзей",
                onShortClick = { println("короткий") },
                onLongClick = { println("длинный") }
            )
        }
    }
}

