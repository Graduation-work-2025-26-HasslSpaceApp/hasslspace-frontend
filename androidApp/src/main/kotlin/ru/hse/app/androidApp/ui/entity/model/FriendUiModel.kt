package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class FriendUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String
)