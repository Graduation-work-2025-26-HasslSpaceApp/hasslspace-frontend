package ru.hse.app.androidApp.screen.auth

import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState
import ru.hse.app.androidApp.ui.entity.model.auth.LoginUserEvent
import ru.hse.app.androidApp.ui.entity.model.auth.RegisterUserEvent
import ru.hse.app.androidApp.ui.entity.model.auth.SavePhotoEvent
import ru.hse.app.androidApp.ui.entity.model.auth.SendVerificationCodeEvent
import ru.hse.app.androidApp.ui.entity.model.auth.VerifyCodeEvent
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val toastManager: ToastManager
//    private val loginUserUseCase: LoginUserUseCase,
//    private val registerUserUseCase: RegisterUserUseCase,
//    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
//    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
//    private val checkVerificationCodeUseCase: CheckVerificationCodeUseCase,
//    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
//    private val errorHandler: ErrorHandler,
//    val cropProfilePhotoService: CropProfilePhotoService,
) : ViewModel() {
    val isLoading = mutableStateOf(false)
    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _registerEvent = MutableStateFlow<RegisterUserEvent?>(null)
    val registerEvent: StateFlow<RegisterUserEvent?> = _registerEvent

    private val _loginEvent = MutableStateFlow<LoginUserEvent?>(null)
    val loginEvent: StateFlow<LoginUserEvent?> = _loginEvent

    private val _savePhotoEvent = MutableStateFlow<SavePhotoEvent?>(null)
    val savePhotoEvent: StateFlow<SavePhotoEvent?> = _savePhotoEvent

    private val _sendCodeEvent = MutableStateFlow<SendVerificationCodeEvent?>(null)
    val sendCodeEvent: StateFlow<SendVerificationCodeEvent?> = _sendCodeEvent

    private val _verifyCodeEvent = MutableStateFlow<VerifyCodeEvent?>(null)
    val verifyCodeEvent: StateFlow<VerifyCodeEvent?> = _verifyCodeEvent

//    val username = mutableStateOf("")
//    val email = mutableStateOf("")
//    val password = mutableStateOf("")
//    val passwordAgain = mutableStateOf("")
//    val selectedImageUri = mutableStateOf<Uri?>(null)
//    val code = mutableStateListOf("", "", "", "", "", "")
//    val jwt = mutableStateOf("")
//    val verificationStatus = mutableStateOf(false)
//    val wasSent = mutableStateOf(false)

    fun clear() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(
                password = "",
                passwordAgain = "",
                jwt = "",
                verificationStatus = false,
                code = listOf("", "", "", "", "", "")
            )
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    //
//    suspend fun registerUser(): Boolean {
//        isLoading.value = true
//
//        if (isValidEmail(email.value) &&
//            isValidPassword(
//                password.value,
//                passwordAgain.value
//            ) &&
//            isValidUsername(username.value)
//        ) {
//            val resultResponse = registerUserUseCase.execute(
//                email.value, password.value, username.value
//            )
//            isLoading.value = false
//            return when (resultResponse) {
//                is ApiCallResult.Error -> {
//                    errorHandler.handleError(resultResponse)
//                    false
//                }
//
//                is ApiCallResult.Success -> {
//                    jwt.value = resultResponse.data
//                    saveJwtToStorage()
//                    true
//                }
//            }
//        }
//        isLoading.value = false
//        return false
//    }
//
//    fun saveJwtToStorage() {
//        viewModelScope.launch {
//            verificationManager.saveJwt(jwt.value)
//        }
//    }
//
//    fun saveVerificationStatusToStorage() {
//        viewModelScope.launch {
//            verificationManager.saveVerificationStatus(verificationStatus.value)
//        }
//    }
//
//    suspend fun checkVerification(): Boolean {
//        isLoading.value = true
//        return try {
//            when (val resultResponse = checkEmailVerificationUseCase.execute()) {
//                is ApiCallResult.Error -> {
//                    errorHandler.handleError(resultResponse)
//                    false
//                }
//
//                is ApiCallResult.Success -> {
//                    val result = resultResponse.data
//                    withContext(Dispatchers.Main) {
//                        verificationStatus.value = result
//                        saveVerificationStatusToStorage()
//                    }
//                    result
//                }
//            }
//        } finally {
//            withContext(Dispatchers.Main) {
//                isLoading.value = false
//            }
//        }
//    }
//
//    suspend fun loginUser(): Boolean {
//        isLoading.value = true
//        if (isValidEmail(email.value) &&
//            isPasswordLongEnough(password.value)
//        ) {
//            val resultResponse = loginUserUseCase.execute(
//                email.value, password.value
//            )
//            isLoading.value = false
//            return when (resultResponse) {
//                is ApiCallResult.Error -> {
//                    errorHandler.handleError(resultResponse)
//                    false
//                }
//
//                is ApiCallResult.Success -> {
//                    jwt.value = resultResponse.data
//                    saveJwtToStorage()
//                    true
//                }
//            }
//        }
//        isLoading.value = false
//        return false
//    }
//
//    suspend fun sendCode(): Boolean {
//        if (wasSent.value) return false
//
//        return try {
//            val responseResult = sendVerificationCodeUseCase.execute(email.value)
//
//            when (responseResult) {
//                is ApiCallResult.Success -> {
//                    wasSent.value = true
//                    true
//                }
//
//                is ApiCallResult.Error -> {
//                    errorHandler.handleError(responseResult)
//                    false
//                }
//            }
//        } catch (e: Exception) {
//            false
//        }
//    }
//
//    suspend fun verifyUser(type: String): Boolean {
//        isLoading.value = true
//        return try {
//            val verificationResult = checkVerificationCodeUseCase.execute(code.joinToString(""))
//            when (verificationResult) {
//                is ApiCallResult.Success -> {
//                    val verificationStatusResult = checkEmailVerificationUseCase.execute()
//                    when (verificationStatusResult) {
//                        is ApiCallResult.Success -> {
//                            if (verificationStatusResult.data) {
//                                verificationStatus.value = true
//                                if (type == "login") {
//                                    saveVerificationStatusToStorage()
//                                }
//                                true
//                            } else {
//                                false
//                            }
//                        }
//
//                        is ApiCallResult.Error -> {
//                            errorHandler.handleError(verificationStatusResult)
//                            false
//                        }
//                    }
//                }
//
//                is ApiCallResult.Error -> {
//                    errorHandler.handleError(verificationResult)
//                    false
//                }
//            }
//        } finally {
//            isLoading.value = false
//        }
//    }
//
//    fun addProfilePhoto() {
//        viewModelScope.launch {
//            selectedImageUri.value?.let {
//                val photoResultResponse = saveUserPhotoUseCase.execute(it, null)
//                if (photoResultResponse is ApiCallResult.Error) {
//                    errorHandler.handleError(photoResultResponse)
//                }
//            }
//        }
//    }
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
        )
    }

    fun onPhotoUriChanged(uri: Uri?) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(selectedImageUri = uri)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun resetSavePhotoEvent() {
        _savePhotoEvent.value = null
    }
}

private fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPasswordLongEnough(password: String): Boolean {
    return password.length >= 8
}

private fun isValidPassword(password: String, passwordAgain: String): Boolean {
    return password.length >= 8 && (password == passwordAgain)
}

private fun isValidUsername(username: String): Boolean {
    return username.isNotEmpty()
}