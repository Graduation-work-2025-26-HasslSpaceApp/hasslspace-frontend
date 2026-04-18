package ru.hse.app.androidApp.ui.entity.model.servers

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel

sealed interface ServersUiState {
    data object Loading : ServersUiState
    data class Success(val data: ServersUiModel) : ServersUiState
    data class Error(val message: String) : ServersUiState
}

@Immutable
data class ServersUiModel(
    val userServers: List<ServerShortUiModel>,
)