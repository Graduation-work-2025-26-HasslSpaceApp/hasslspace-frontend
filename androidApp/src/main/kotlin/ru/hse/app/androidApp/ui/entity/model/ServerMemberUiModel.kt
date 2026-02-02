package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class ServerMemberUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
    val mainRole: RoleMiniUiModel? = null,
    val allRoles: List<RoleMiniUiModel> = listOf(),
    val isOwner: Boolean = false,
)