package ru.hse.app.hasslspace.ui.entity.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.hse.app.hasslspace.domain.model.entity.RoleInfo

@Immutable
data class RoleMiniCheckboxUiModel(
    val id: String,
    val title: String,
    val color: Color,
    var isChosen: Boolean
)

fun RoleInfo.toRoleMiniCheckboxUiModel(): RoleMiniCheckboxUiModel {
    return RoleMiniCheckboxUiModel(
        id = this.id,
        title = this.name,
        color = Color(this.color.toColorInt()),
        isChosen = false
    )
}

fun RoleMiniCheckboxUiModel.toRoleMiniIfChosen(): RoleMiniUiModel? {
    return if (this.isChosen) {
        RoleMiniUiModel(
            id = this.id,
            title = this.title,
            color = this.color,
        )
    } else {
        null
    }
}