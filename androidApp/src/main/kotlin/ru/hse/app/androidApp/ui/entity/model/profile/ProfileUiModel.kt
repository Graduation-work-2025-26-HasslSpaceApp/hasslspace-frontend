package ru.hse.app.androidApp.ui.entity.model.profile

import android.net.Uri
import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfo
import ru.hse.app.androidApp.ui.entity.model.FriendExtendedInfoUiModel
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val data: ProfileUiModel) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

@Immutable
data class ProfileUiModel(
    val username: String,
    val nickname: String,
    val email: String,
    val status: StatusPresentation,
    val profilePictureUrl: String,
    val description: String,
    val friends: List<FriendUiModel>,
    val applications: List<FriendUiModel>,
    val servers: List<ServerShortUiModel>,
    val isDarkCheck: Boolean,
    val selectedImageUri: Uri?,
    val chosenUser: FriendExtendedInfoUiModel?,
    val chosenUserCommonServers: List<ServerShortUiModel>
)

fun getEmptyUiModel(): ProfileUiModel {
    return ProfileUiModel(
        username = "",
        nickname = "",
        email = "",
        description = "",
        status = StatusPresentation.INVISIBLE,
        profilePictureUrl = "",
        friends = listOf(),
        servers = listOf(),
        applications = listOf(),
        isDarkCheck = false,
        selectedImageUri = null,
        chosenUser = null,
        chosenUserCommonServers = listOf()
    )
}

fun UserExpandedInfo.toUI(): ProfileUiModel {
    return ProfileUiModel(
        username = this.username,
        nickname = this.nickname,
        status = this.status.toStatusPresentation(),
        email = this.email,
        profilePictureUrl = this.avatarUrl ?: "",
        description = this.description,
        friends = listOf(),
        servers = listOf(),
        applications = listOf(),
        isDarkCheck = false,
        selectedImageUri = null,
        chosenUser = null,
        chosenUserCommonServers = listOf()
    )
}