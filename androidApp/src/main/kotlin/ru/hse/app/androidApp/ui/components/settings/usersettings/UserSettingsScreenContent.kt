package ru.hse.app.androidApp.ui.components.settings.usersettings

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UserSettingsScreenContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    selectedStatus: StatusPresentation,
    onStatusArrowClick: () -> Unit,
    selectedImageUri: Uri?,
    onPhotoPickClick: () -> Unit,
    editedUsername: String,
    onEditedUsernameChanged: (String) -> Unit,
    enabledChangeUsername: Boolean,
    onApplyNewUsername: () -> Unit,
    description: String,
    onDescChanged: (String) -> Unit,
    onApplyDescClick: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.background_up_dark else R.drawable.background_up_light),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .offset(y = (-10).dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 50.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BackButton(onClick = onBackClick)

            Spacer(Modifier.height(40.dp))

            VariableBold(
                text = "Пользовательские настройки",
                fontSize = 20.sp,
            )

            Spacer(Modifier.height(25.dp))

            StatusSetting(
                selectedOption = selectedStatus,
                onArrowClick = onStatusArrowClick
            )

            PhotoSetting(
                selectedImageUri = selectedImageUri,
                onPhotoPickClick = onPhotoPickClick
            )

            Spacer(Modifier.height(10.dp))

            UsernameSetting(
                editedUsername = editedUsername,
                onApplyClick = onApplyNewUsername,
                enabled = enabledChangeUsername,
                onEditedUsernameChanged = onEditedUsernameChanged
            )

            DescriptionSetting(
                description = description,
                onDescChanged = onDescChanged,
                onApplyClick = onApplyDescClick
            )

            Exit(onClick = onExit)
        }
    }
}

@Preview(
    name = "User Settings - Theme Toggle",
    showBackground = true,
    widthDp = 360
)
@Composable
fun UserSettingsContentPreview() {
    val isDarkTheme = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(StatusPresentation.ONLINE) }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(
            Uri.parse("android.resource://ru.hse.app.androidApp/drawable/avatar_default_light")
        )
    }
    val description = remember { mutableStateOf("Рассказываю о себе рассказываю о себе") }
    val username = "username"

    AppTheme(isDark = isDarkTheme.value) {
        UserSettingsScreenContent(
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            isDarkTheme = isDarkTheme.value,
            selectedStatus = selectedOption.value,
            onStatusArrowClick = {},
            selectedImageUri = selectedImageUri.value,
            onPhotoPickClick = {},
            editedUsername = username,
            enabledChangeUsername = true,
            onApplyNewUsername = {},
            description = description.value,
            onDescChanged = {},
            onApplyDescClick = {},
            onExit = {},
            onEditedUsernameChanged = {}
        )
    }
}

@Preview(
    name = "User Settings - Theme Toggle",
    showBackground = true,
    widthDp = 360
)
@Composable
fun UserSettingsContentPreview1() {
    val isDarkTheme = remember { mutableStateOf(true) }
    val selectedOption = remember { mutableStateOf(StatusPresentation.ONLINE) }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(
            Uri.parse("android.resource://ru.hse.app.androidApp/drawable/avatar_default_light")
        )
    }
    val description = remember { mutableStateOf("Рассказываю о себе рассказываю о себе") }
    val username = "username"

    AppTheme(isDark = isDarkTheme.value) {
        UserSettingsScreenContent(
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            isDarkTheme = isDarkTheme.value,
            selectedStatus = selectedOption.value,
            onStatusArrowClick = {},
            selectedImageUri = selectedImageUri.value,
            onPhotoPickClick = {},
            editedUsername = username,
            enabledChangeUsername = true,
            onApplyNewUsername = {},
            description = description.value,
            onDescChanged = {},
            onApplyDescClick = {},
            onExit = {},
            onEditedUsernameChanged = {}
        )
    }
}