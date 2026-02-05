package ru.hse.app.androidApp.ui.entity.model.auth

import android.net.Uri
import androidx.compose.runtime.Immutable

sealed interface AuthUiState {
    data object Loading : AuthUiState
    data class Success(val data: AuthUiModel) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

@Immutable
data class AuthUiModel(
    val username: String,
    val email: String,
    val nickname: String,
    val password: String,
    val passwordAgain: String,
    val selectedImageUri: Uri?,
    val code: List<String>,
    val jwt: String,
    val verificationStatus: Boolean,
    val wasSent: Boolean
)

fun getEmptyUiModel(): AuthUiModel {
    return AuthUiModel(
        username = "",
        email = "",
        nickname = "",
        password = "",
        passwordAgain = "",
        selectedImageUri = null,
        code = mutableListOf("", "", "", "", "", ""),
        jwt = "",
        verificationStatus = false,
        wasSent = false
    )
}