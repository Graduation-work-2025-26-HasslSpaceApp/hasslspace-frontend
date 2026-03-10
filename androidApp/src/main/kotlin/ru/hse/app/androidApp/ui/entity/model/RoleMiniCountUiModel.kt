package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import ru.hse.app.androidApp.ui.entity.model.serversettings.RoleInfoUiModel

@Immutable
data class RoleMiniCountUiModel(
    val id: String,
    val title: String,
    val color: Color,
    val count: Int
)

fun RoleInfoUiModel.toRoleMiniCount() : RoleMiniCountUiModel {
    return RoleMiniCountUiModel(
        id = this.id,
        title = this.name,
        color = this.color,
        count = this.members.size
    )
}