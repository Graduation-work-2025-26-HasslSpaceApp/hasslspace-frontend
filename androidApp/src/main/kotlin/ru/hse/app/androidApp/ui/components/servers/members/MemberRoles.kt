package ru.hse.app.androidApp.ui.components.servers.members

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.RoleMiniUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun MemberRoles(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    roles: List<RoleMiniUiModel>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(all = 16.dp),
    ) {
        for (role in roles) {
            Row(
                modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(role.color, shape = CircleShape),
                )
                Spacer(Modifier.width(8.dp))
                VariableLight(
                    fontSize = 16.sp,
                    text = role.title
                )
            }
            Spacer(Modifier.height(5.dp))
        }
        Spacer(Modifier.height(5.dp))
        VariableLight(
            fontSize = 16.sp,
            text = "Редактировать роли",
            modifier = Modifier.clickable(
                onClick = onEditClick
            )
        )
    }
}

private val roles = listOf(
    RoleMiniUiModel("13", "role 1", Color.Blue),
    RoleMiniUiModel("14", "role 2", Color.Red),
    RoleMiniUiModel("15", "role 3", Color.Magenta),
    RoleMiniUiModel("16", "role 4", Color.Yellow),
)

@Preview(showBackground = true)
@Composable
fun MemberRolesPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        MemberRoles(
            onEditClick = {},
            roles = roles,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemberRolesPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        MemberRoles(
            onEditClick = {},
            roles = roles,
        )
    }
}