package ru.hse.app.androidApp.ui.entity.model.serversettings

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation

@Immutable
data class RoleMemberUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
)