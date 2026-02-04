package ru.hse.app.androidApp.ui.components.servers.editserver

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.settings.usersettings.PhotoSetting
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun SettingsServer(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
    onPhotoPickClick: () -> Unit,
    editedServerName: String,
    onEditedServernameChanged: (String) -> Unit,
    enabledChangeServerName: Boolean,
    onApplyNewServerName: () -> Unit,
    onMembersClick: () -> Unit,
    onRolesClick: () -> Unit,
    onInvitationsClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))
            VariableBold(
                text = "Настройки сервера",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(20.dp))

        PhotoSetting(
            selectedImageUri = selectedImageUri,
            onPhotoPickClick = onPhotoPickClick
        )

        Spacer(Modifier.height(15.dp))

        ServernameSetting(
            editedServername = editedServerName,
            onApplyClick = onApplyNewServerName,
            enabled = enabledChangeServerName,
            onEditedServernameChanged = onEditedServernameChanged
        )
        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMembersClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            VariableLight(
                text = "Участники",
                fontSize = 16.sp,
            )
            Spacer(Modifier.width(5.dp))

            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Accept",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(14.dp)
            )
        }
        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRolesClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            VariableLight(
                text = "Роли",
                fontSize = 16.sp,
            )
            Spacer(Modifier.width(5.dp))

            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Accept",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(14.dp)
            )
        }
        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onInvitationsClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            VariableLight(
                text = "Приглашения",
                fontSize = 16.sp,
            )
            Spacer(Modifier.width(5.dp))

            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Accept",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(14.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsServerPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        SettingsServer(
            onBackClick = {},
            selectedImageUri = mutableStateOf(Uri.EMPTY),
            onPhotoPickClick = {},
            editedServerName = "Тест сервер",
            onEditedServernameChanged = {},
            enabledChangeServerName = true,
            onApplyNewServerName = {},
            onMembersClick = {},
            onRolesClick = {},
            onInvitationsClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsServerPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        SettingsServer(
            onBackClick = {},
            selectedImageUri = mutableStateOf(Uri.EMPTY),
            onPhotoPickClick = {},
            editedServerName = "Тест сервер",
            onEditedServernameChanged = {},
            enabledChangeServerName = true,
            onApplyNewServerName = {},
            onMembersClick = {},
            onRolesClick = {},
            onInvitationsClick = {}
        )
    }
}
