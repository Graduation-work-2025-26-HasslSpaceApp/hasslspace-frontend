package ru.hse.app.androidApp.ui.components.common.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun SearchBar(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Поиск",
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (text.isEmpty()) {
                    VariableLight(
                        text = placeholder,
                        fontSize = 16.sp,
                        fontColor = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = "Очистить поиск",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onValueChange("")
                        }
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreviewLight() {
    val text = remember { mutableStateOf("") }
    AppTheme(
        isDark = false
    ) {
        SearchBar(
            text = text.value,
            onValueChange = { text.value = it }
        )
    }
}

@Preview
@Composable
fun SearchBarPreviewLightNotEmpty() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = false
    ) {
        SearchBar(
            text = text.value,
            onValueChange = { text.value = it }
        )
    }
}

@Preview
@Composable
fun SearchBarPreviewDark() {
    val text = remember { mutableStateOf("") }
    AppTheme(
        isDark = true
    ) {
        SearchBar(
            text = text.value,
            onValueChange = { text.value = it }
        )
    }
}

@Preview
@Composable
fun SearchBarPreviewDarkNotEmpty() {
    val text = remember { mutableStateOf("sdfsf") }
    AppTheme(
        isDark = true
    ) {
        SearchBar(
            text = text.value,
            onValueChange = { text.value = it }
        )
    }
}
