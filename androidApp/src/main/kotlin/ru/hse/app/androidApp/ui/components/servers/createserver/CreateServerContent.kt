package ru.hse.app.androidApp.ui.components.servers.createserver

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.auth.photoloading.PhotoPicker
import ru.hse.app.androidApp.ui.components.common.button.ApplyButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.button.BigButton
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CreateServerContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    selectedImageUri: Uri?,
    onPickImageClick: () -> Unit,
    serverName: String,
    onServerNameChanged: (String) -> Unit,
    onCreateServerClick: () -> Unit,
    onJoinClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
            .imePadding()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.height(15.dp))

            VariableMedium(
                text = "Создайте свой сервер",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp)
            )

            VariableLight(
                text = "Ваш сервер - это место, где вы можете тусоваться со \n своими друзьями.\n" +
                        "Создайте сервер и начните общаться",
                fontSize = 12.sp,
                fontColor = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            //TODO перенести в общие компоненты
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PhotoPicker(
                    imageUri = selectedImageUri,
                    onClick = onPickImageClick,
                    sizeCircle = 126.dp,
                    sizeIcon = 30.dp,
                    border = 1.dp
                )
            }

            Spacer(Modifier.height(25.dp))

            AuthCustomField(
                text = serverName,
                onTextChanged = onServerNameChanged,
                placeholder = "Введите название сервера",
                description = "Название сервера",
                maxCharacters = 30,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(15.dp))

            BigButton(
                onClick = onCreateServerClick,
                text = "Создать сервер"
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VariableMedium(
                text = "У вас уже есть приглашение?",
                fontSize = 20.sp
            )

            ApplyButton(
                onClick = onJoinClick,
                text = "Присоединиться к серверу",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun CreateServerContentPreviewLightEmpty() {
    AppTheme(isDark = false) {
        CreateServerContent(
            selectedImageUri = null,
            onPickImageClick = {},
            onBackClick = {},
            serverName = "консультация матан",
            onServerNameChanged = {},
            onCreateServerClick = {},
            onJoinClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun CreateServerContentPreviewDarkEmpty() {
    AppTheme(isDark = true) {
        CreateServerContent(
            selectedImageUri = null,
            onPickImageClick = {},
            onBackClick = {},
            serverName = "консультация матан",
            onServerNameChanged = {},
            onCreateServerClick = {},
            onJoinClick = {}
        )
    }
}