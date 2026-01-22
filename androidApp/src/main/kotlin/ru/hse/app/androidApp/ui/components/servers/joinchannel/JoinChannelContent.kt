package ru.hse.app.androidApp.ui.components.servers.joinchannel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.button.BigButton
import ru.hse.app.androidApp.ui.components.common.field.LinkCustomField
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun JoinChannelContent(
    onBackClick: () -> Unit,
    linkText: MutableState<String>,
    onButtonCLick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.height(15.dp))

            VariableMedium(
                text = "Присоединиться к \n существующему серверу",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            VariableLight(
                text = "Введите приглашение, чтобы присоединиться к \n существующему серверу",
                fontSize = 14.sp,
                fontColor = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))

            LinkCustomField(
                text = linkText,
                placeholder = "Введите ссылку-приглашение",
                description = "Ссылка-приглашение",
                maxCharacters = null,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(25.dp))

            Text(
                text = buildAnnotatedString {
                    append("Приглашения должны выглядеть так «")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        append("https://hasslspace.ru/hTKzmak")
                    }
                    append("»")
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(Modifier.weight(1f))

            BigButton(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = onButtonCLick,
                text = "Присоединиться по ссылке-приглашению",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun JoinChannelContentPreviewLight() {
    AppTheme(isDark = false) {
        JoinChannelContent(
            onBackClick = {},
            linkText = remember { mutableStateOf("https://hasslspace.ru/hTKzmak") },
            onButtonCLick = {},
        )
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun JoinChannelContentPreviewDark() {
    AppTheme(isDark = true) {
        JoinChannelContent(
            onBackClick = {},
            linkText = remember { mutableStateOf("https://haserslspace.ru/hTKzmak") },
            onButtonCLick = {},
        )
    }
}