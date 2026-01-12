package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserShortInfoUiModel(
//    val id: String, //TODO добавим на этапе работы с бизнес-логикой
    val username: String,
    val nickname: String,
    val profilePhotoUrl: String
)