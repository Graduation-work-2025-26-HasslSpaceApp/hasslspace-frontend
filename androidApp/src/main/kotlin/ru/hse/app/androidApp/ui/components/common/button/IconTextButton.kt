package ru.hse.app.androidApp.ui.components.common.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun IconTextButton(
    onClick: () -> Unit,
    text: String,
    iconResource: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(31.dp)
                .clip(CircleShape)
                .background(Color.LightGray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(20.dp))

        VariableMedium(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Preview(showBackground = true)
@Composable
fun IconTextButtonPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconTextButton(
                onClick = {},
                iconResource = R.drawable.new_group_light,
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextButtonPreviewAllLightFriend() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconTextButton(
                onClick = {},
                iconResource = R.drawable.new_friend_light,
                text = "Добавить друга"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextButtonPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconTextButton(
                onClick = {},
                iconResource = R.drawable.new_group_dark,
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextButtonPreviewAllDarkFriend() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconTextButton(
                onClick = {},
                iconResource = R.drawable.new_friend_dark,
                text = "Добавить друга"
            )
        }
    }
}