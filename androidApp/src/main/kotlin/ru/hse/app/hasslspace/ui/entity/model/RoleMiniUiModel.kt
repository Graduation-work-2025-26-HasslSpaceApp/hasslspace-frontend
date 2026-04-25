package ru.hse.app.hasslspace.ui.entity.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class RoleMiniUiModel(
    val id: String,
    val title: String,
    val color: Color,
)