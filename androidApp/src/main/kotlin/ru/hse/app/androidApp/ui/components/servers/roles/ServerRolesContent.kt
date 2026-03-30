package ru.hse.app.androidApp.ui.components.servers.roles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.RoleCardArrow
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCountUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ServerRolesContent(
    roles: List<RoleMiniCountUiModel>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onRoleClick: (RoleMiniCountUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(onClick = onBackClick)

            Spacer(Modifier.weight(0.15f))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                VariableBold(
                    text = "Роли на сервере",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            AddTextButton(
                text = "Добавить",
                onClick = onAddClick
            )
        }
        Spacer(Modifier.height(15.dp))

        if (roles.isEmpty()) {
            NoItemsBox("Ролей пока нет")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(roles, key = { it.id }) { role ->
                RoleCardArrow(
                    title = role.title,
                    color = role.color,
                    onArrowClick = { onRoleClick(role) },
                    membersCount = role.count,
                    height = 50.dp
                )
            }
        }
    }
}

private val roles = listOf(
    RoleMiniCountUiModel("13", "role 1", Color.Blue, 10),
    RoleMiniCountUiModel("14", "role 2", Color.Red, 3),
    RoleMiniCountUiModel("15", "role 3", Color.Magenta, 4),
    RoleMiniCountUiModel("16", "role 4", Color.Yellow, 10),
    RoleMiniCountUiModel("3", "role 1", Color.Blue, 6),
    RoleMiniCountUiModel("4", "role 2", Color.Red, 59),
    RoleMiniCountUiModel("5", "role 3", Color.Magenta, 1),
    RoleMiniCountUiModel("6", "role 4", Color.Yellow, 0),
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun ServerRolesPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        ServerRolesContent(
            roles = roles,
            onBackClick = {},
            onAddClick = {},
            onRoleClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun ServerRolesPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        ServerRolesContent(
            roles = roles,
            onBackClick = {},
            onAddClick = {},
            onRoleClick = {},
        )
    }
}