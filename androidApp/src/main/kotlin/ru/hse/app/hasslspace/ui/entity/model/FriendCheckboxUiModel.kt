package ru.hse.app.hasslspace.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class FriendCheckboxUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
    var isChosen: Boolean
)