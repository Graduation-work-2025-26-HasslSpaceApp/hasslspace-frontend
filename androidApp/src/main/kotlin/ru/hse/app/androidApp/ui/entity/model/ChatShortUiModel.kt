package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChatShortUiModel(
    val id: String,
    val title: String,
    val lastMessage: String,
    val timeOfLastMessage: String,
    val chatPhotoUrl: String,
    val unreadCount: Int
)