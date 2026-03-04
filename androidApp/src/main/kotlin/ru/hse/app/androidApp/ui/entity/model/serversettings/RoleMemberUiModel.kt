package ru.hse.app.androidApp.ui.entity.model.serversettings

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.Type
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.TypeUiModel
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation

@Immutable
data class RoleMemberUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
)