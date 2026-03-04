package ru.hse.app.androidApp.ui.entity.model.serversettings

import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation

data class RoleInfoUiModel (
    val id: String,
    val name: String,
    val color: Color,
    val members: List<RoleMemberUiModel>,
) {}

fun RoleInfo.toRoleInfoUiModel() : RoleInfoUiModel {
    return RoleInfoUiModel (
        id = this.id,
        name = this.name,
        color = Color(this.color.toColorInt()),
        members = this.members.map { member ->
            RoleMemberUiModel(
                id = member.id,
                name = member.name,
                nickname = member.username,
                status = member.status.toStatusPresentation(),
                avatarUrl = member.photoURL?: "",
            )
        }
    )
}