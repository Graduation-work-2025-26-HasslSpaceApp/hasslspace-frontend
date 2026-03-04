package ru.hse.app.androidApp.ui.components.servers.members

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardThin
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.settings.usersettings.Exit
import ru.hse.app.androidApp.ui.entity.model.RoleMiniUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun EditMemberContent(
    imageLoader: ImageLoader,
    member: ServerMemberUiModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    onEditRoles: () -> Unit,
    onDeleteMember: (ServerMemberUiModel) -> Unit,
    onTransferRightsToMember: (ServerMemberUiModel) -> Unit
) {
    BackHandler {
        onBackClick()
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                indication = null,
                onClick = {},
                enabled = false,
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(top = 50.dp)
            .padding(16.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))
            VariableBold(
                text = "Редактировать участника",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(15.dp))

        UserCardThin(
            imageLoader = imageLoader,
            username = member.name,
            nickname = member.nickname,
            isDarkTheme = isDarkTheme,
            status = member.status,
            profilePictureUrl = member.avatarUrl,
        )

        Spacer(Modifier.height(15.dp))

        VariableLight(
            fontSize = 16.sp,
            text = "Роли"
        )

        Spacer(Modifier.height(10.dp))

        MemberRoles(
            onEditClick = onEditRoles,
            roles = member.allRoles
        )

        Spacer(Modifier.height(30.dp))

        Exit(
            text = "Удалить \"${member.name}\" с сервера",
            onClick = { onDeleteMember(member) }
        )

        Spacer(Modifier.height(15.dp))

        Exit(
            text = "Передать \"${member.name}\" права на сервер",
            onClick = { onTransferRightsToMember(member) }
        )
    }
}

private val roles = listOf(
    RoleMiniUiModel("13", "role 1", Color.Blue),
    RoleMiniUiModel("14", "role 2", Color.Red),
    RoleMiniUiModel("15", "role 3", Color.Magenta),
    RoleMiniUiModel("16", "role 4", Color.Yellow),
)

private val member = ServerMemberUiModel(
    id = "1",
    name = "Александр Иванов",
    nickname = "Александр Иванов".lowercase().replace(" ", "_"),
    avatarUrl = "",
    status = StatusPresentation.ACTIVE,
    mainRole = roles[1],
    allRoles = roles
)

private val member1 = ServerMemberUiModel(
    id = "1",
    name = "Александр Иванов",
    nickname = "Александр Иванов".lowercase().replace(" ", "_"),
    avatarUrl = "",
    status = StatusPresentation.ACTIVE,
    mainRole = roles[1],
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun EditMemberContentPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        EditMemberContent(
            imageLoader = LocalContext.current.imageLoader,
            onBackClick = {},
            member = member,
            onDeleteMember = {},
            onTransferRightsToMember = {},
            isDarkTheme = false,
            onEditRoles = {}
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun EditMemberContentPreviewWithRequestsLight1() {

    AppTheme(isDark = false) {
        EditMemberContent(
            imageLoader = LocalContext.current.imageLoader,
            onBackClick = {},
            member = member1,
            onDeleteMember = {},
            onTransferRightsToMember = {},
            isDarkTheme = false,
            onEditRoles = {}
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - Only Friends (Light)")
@Composable
fun EditMemberContentPreviewOnlyFriendsLight() {

    AppTheme(isDark = true) {
        EditMemberContent(
            imageLoader = LocalContext.current.imageLoader,
            onBackClick = {},
            member = member,
            onDeleteMember = {},
            onTransferRightsToMember = {},
            isDarkTheme = true,
            onEditRoles = {}
        )
    }
}