package ru.hse.app.androidApp.ui.entity.model.servers

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.FriendUiModelInvitation

sealed interface ServerCardUiState {
    data object Loading : ServerCardUiState
    data class Success(val data: ServerCardUiModel) : ServerCardUiState
    data class Error(val message: String) : ServerCardUiState
}

//TODO
@Immutable
data class ServerCardUiModel(
    val chosenServer: ServerExpandedUiModel,
    val friendsNotInServer: List<FriendUiModelInvitation>
)