package ru.hse.app.hasslspace.ui.components.common.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun TextArrowButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        VariableMedium(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.width(4.dp))

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
fun TextArrowButtonPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextArrowButton(
                onClick = {},
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextArrowButtonPreviewAllLightFriend() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextArrowButton(
                onClick = {},
                text = "Добавить друга"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextArrowButtonPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextArrowButton(
                onClick = {},
                text = "Новая группа"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextArrowButtonPreviewAllDarkFriend() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextArrowButton(
                onClick = {},
                text = "Добавить друга"
            )
        }
    }
}