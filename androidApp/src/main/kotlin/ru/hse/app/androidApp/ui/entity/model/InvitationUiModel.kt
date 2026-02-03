package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class InvitationUiModel(
    val id: String,
    val userName: String,
    val avatarUrl: String,
    val expirationDate: LocalDateTime
)