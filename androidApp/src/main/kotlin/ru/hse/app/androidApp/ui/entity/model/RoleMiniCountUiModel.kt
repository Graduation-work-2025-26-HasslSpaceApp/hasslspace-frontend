package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class RoleMiniCountUiModel(
    val id: String,
    val title: String,
    val color: Color,
    val count: Int
)