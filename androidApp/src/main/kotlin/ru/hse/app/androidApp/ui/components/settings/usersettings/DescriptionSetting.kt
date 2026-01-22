package ru.hse.app.androidApp.ui.components.settings.usersettings

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun DescriptionSetting(
    description: MutableState<String>,
    maxCharacters: Int = 1000,
    modifier: Modifier = Modifier,
    onApplyClick: () -> Unit,
) {
    val containerBackgroundColor = Color.Transparent
    val isMaxReached = description.value.length == maxCharacters

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(containerBackgroundColor),
    ) {

        VariableMedium(
            text = "Обо мне",
            fontSize = 14.sp,
            fontColor = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(Modifier.height(6.dp))

        CustomTextWindow(
            text = description,
            placeholder = "Расскажите о себе",
        )
        Spacer(Modifier.height(10.dp))

        AddTextButton(
            onClick = onApplyClick,
            text = "Применить",
            modifier = Modifier.align(Alignment.End)
        )

    }
}

@Composable
fun CustomTextWindow(
    text: MutableState<String>,
    placeholder: String = "Оставьте отзыв...",
    maxCharacters: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val isMaxReached = text.value.length == maxCharacters

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                if (it.length <= maxCharacters) {
                    text.value = it
                }
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${text.value.length} / $maxCharacters",
                        fontSize = 12.sp,
                        color = if (isMaxReached) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                    )
                }
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewBoxPreview() {
    val description = remember { mutableStateOf("Рассказываю о себе рассказываю о себе") }
    AppTheme(isDark = false) {
        DescriptionSetting(
            description = description,
            onApplyClick = {}
        )
    }
}