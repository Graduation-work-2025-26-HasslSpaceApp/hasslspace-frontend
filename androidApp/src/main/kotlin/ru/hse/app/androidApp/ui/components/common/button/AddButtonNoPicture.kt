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
import androidx.compose.material3.Icon
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
fun AddButtonNoPicture(
    onClick: () -> Unit,
    text: String,
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

        VariableMedium(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            painter = painterResource(R.drawable.arrow),
            contentDescription = "go to",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(14.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonNoPicturePreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            AddButtonNoPicture(
                onClick = {},
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonNoPicturePreviewAllLightFriend() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            AddButtonNoPicture(
                onClick = {},
                text = "Добавить друга"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonNoPicturePreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AddButtonNoPicture(
                onClick = {},
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonNoPicturePreviewAllDarkFriend() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AddButtonNoPicture(
                onClick = {},
                text = "Добавить друга"
            )
        }
    }
}