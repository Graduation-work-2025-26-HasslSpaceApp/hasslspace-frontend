package ru.hse.app.hasslspace.ui.components.servers.joinserver

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.bar.SearchBar
import ru.hse.app.hasslspace.ui.components.common.button.BackButton
import ru.hse.app.hasslspace.ui.components.common.button.BigButton
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun JoinServerContent(
    onBackClick: () -> Unit,
    linkText: String,
    onLinkTextChanged: (String) -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exampleLink = "https://hasslspace.ru/hTKzmak"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
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

            SearchBar(
                text = linkText,
                placeholder = "Введите код-приглашение",
                onValueChange = onLinkTextChanged,
                modifier = Modifier.fillMaxWidth()
            )

//            LinkCustomField(
//                text = linkText,
//                onStringChanged = onLinkTextChanged,
//                placeholder = "Введите код-приглашение",
//                description = "Код-приглашение",
//                maxCharacters = null,
//                modifier = Modifier.fillMaxWidth()
//            )

            Spacer(Modifier.height(25.dp))

//            SelectionContainer {
//                Text(
//                    text = buildAnnotatedString {
//                        append("Приглашения должны выглядеть так \n«")
//                        withStyle(
//                            style = SpanStyle(
//                                color = MaterialTheme.colorScheme.onBackground
//                            )
//                        ) {
//                            append("https://hasslspace.ru/hTKzmak")
//                        }
//                        append("»")
//                    },
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Light,
//                    color = MaterialTheme.colorScheme.outline,
//                )
//            }


            Spacer(Modifier.weight(1f))

            BigButton(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = onButtonClick,
                text = "Присоединиться по коду-приглашению",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun JoinServerContentPreviewLight() {
    AppTheme(isDark = false) {
        JoinServerContent(
            onBackClick = {},
            linkText = "https://hasslspace.ru/hTKzmak",
            onLinkTextChanged = {},
            onButtonClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun JoinServerContentPreviewDark() {
    AppTheme(isDark = true) {
        JoinServerContent(
            onBackClick = {},
            linkText = "https://haserslspace.ru/hTKzmak",
            onLinkTextChanged = {},
            onButtonClick = {},
        )
    }
}