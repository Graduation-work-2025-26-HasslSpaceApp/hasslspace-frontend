package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class RoleMiniCheckboxUiModel(
    val id: String,
    val title: String,
    val color: Color,
    var isChosen: Boolean
)