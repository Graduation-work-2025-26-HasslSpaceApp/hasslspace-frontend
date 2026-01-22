package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class TextChannelUiModel(
    val id: String,
    val title: String,
)