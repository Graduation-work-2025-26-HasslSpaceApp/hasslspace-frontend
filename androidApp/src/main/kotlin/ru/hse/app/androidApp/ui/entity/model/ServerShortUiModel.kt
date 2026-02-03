package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class ServerShortUiModel(
    val id: String,
    val name: String,
    val participantCount: Int,
    val avatarUrl: String
)