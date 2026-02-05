package ru.hse.app.androidApp.ui.components.servers.editserver


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.components.auth.register.UsernameField
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField

@Composable
fun ServernameSetting(
    editedServername: String,
    onEditedServernameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onApplyClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AuthCustomField(
            text = editedServername,
            onTextChanged = onEditedServernameChanged,
            placeholder = "Введите название сервера",
            description = "Название сервера",
            maxCharacters = 30,
            modifier = Modifier.fillMaxWidth()
        )

        AddTextButton(
            onClick = onApplyClick,
            enabled = enabled,
            text = "Применить",
            modifier = Modifier.align(Alignment.End)
        )
    }
}