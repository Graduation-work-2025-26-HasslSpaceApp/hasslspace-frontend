package ru.hse.app.androidApp.ui.components.common.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun LinkCustomField(
    text: String,
    onStringChanged: (String) -> Unit,
    placeholder: String = "Ссылка-приглашение",
    description: String = "Ссылка-приглашение",
    maxCharacters: Int? = 20,
    modifier: Modifier = Modifier
) {
    val isMaxReached = maxCharacters != null && text.length == maxCharacters

    val linkRegex = Regex("^https://hasslspace\\.ru/[a-zA-Z0-9]+$")

    val isError = !text.matches(linkRegex)

    Column(
        modifier = modifier
    ) {
        VariableMedium(
            text = description,
            fontSize = 14.sp,
            fontColor = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
                    onStringChanged(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isError) {
                        Text(
                            text = "Некооректный формат ссылки",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (maxCharacters != null) {
                        Text(
                            text = "${text.length} / $maxCharacters",
                            fontSize = 12.sp,
                            color = if (isMaxReached) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            isError = isError,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun LinkCustomFieldPreviewLightCorrect() {
    AppTheme(
        isDark = false
    ) {
        LinkCustomField(
            text = "https://hasslspace.ru/hTKzmak",
            onStringChanged = {},
            placeholder = "Ссылка",
            description = "Ссылка",
            maxCharacters = null
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LinkCustomFieldPreviewDarkEmpty() {
    AppTheme(
        isDark = true
    ) {
        LinkCustomField(
            text = "https://hasslукspace.ru/hTKzmak",
            onStringChanged = {},
            placeholder = "Ссылка",
            description = "Ссылка",
            maxCharacters = null
        )
    }
}
