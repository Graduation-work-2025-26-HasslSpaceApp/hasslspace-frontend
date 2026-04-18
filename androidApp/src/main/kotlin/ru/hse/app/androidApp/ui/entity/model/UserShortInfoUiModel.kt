package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserShortInfoUiModel(
    val username: String,
    val nickname: String,
    val profilePhotoUrl: String
)