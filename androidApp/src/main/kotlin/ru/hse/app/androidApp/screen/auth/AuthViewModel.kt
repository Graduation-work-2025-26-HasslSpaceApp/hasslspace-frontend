package ru.hse.app.androidApp.screen.auth

import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.usecase.auth.CheckEmailVerificationUseCase
import ru.hse.app.androidApp.domain.usecase.auth.CheckVerificationCodeUseCase
import ru.hse.app.androidApp.domain.usecase.auth.LoginUserUseCase
import ru.hse.app.androidApp.domain.usecase.auth.RegisterUserUseCase
import ru.hse.app.androidApp.domain.usecase.auth.SendVerificationCodeUseCase
import ru.hse.app.androidApp.domain.usecase.profile.SaveUserPhotoUseCase
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiModel
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState
import ru.hse.app.androidApp.ui.entity.model.auth.CheckEmailVerificationEvent
import ru.hse.app.androidApp.ui.entity.model.auth.LoginUserEvent
import ru.hse.app.androidApp.ui.entity.model.auth.RegisterUserEvent
import ru.hse.app.androidApp.ui.entity.model.auth.SavePhotoEvent
import ru.hse.app.androidApp.ui.entity.model.auth.SendVerificationCodeEvent
import ru.hse.app.androidApp.ui.entity.model.auth.VerifyCodeEvent
import ru.hse.app.androidApp.ui.entity.model.auth.VerifyUserEvent
import ru.hse.app.androidApp.ui.entity.model.auth.getEmptyUiModel
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
    private val checkVerificationCodeUseCase: CheckVerificationCodeUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
//    private val errorHandler: ErrorHandler,
    val cropProfilePhotoService: CropProfilePhotoService,
) : ViewModel() {
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

    private val _verifyUserEvent = MutableStateFlow<VerifyUserEvent?>(null)
    val verifyUserEvent: StateFlow<VerifyUserEvent?> = _verifyUserEvent


    private val _checkEmailVerificationEvent = MutableStateFlow<CheckEmailVerificationEvent?>(null)
    val checkEmailVerificationEvent: StateFlow<CheckEmailVerificationEvent?> =
        _checkEmailVerificationEvent

    init {
        loadAuthInfo()
    }

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

    fun loadAuthInfo() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            _uiState.value = AuthUiState.Success(
                data = getEmptyUiModel()
            )
        }
    }


    fun registerUser() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data

            viewModelScope.launch {
                if (isValidEmail(data.email) &&
                    isValidPassword(
                        data.password,
                        data.passwordAgain
                    ) &&
                    isValidUsername(data.username) &&
                    isValidNickname(data.nickname)
                ) {
                    val result = registerUserUseCase(
                        email = data.email,
                        username = data.username,
                        nickname = data.nickname,
                        password = data.password
                    )

                    _registerEvent.value = result.fold(
                        onSuccess = { jwt ->
                            onJwtChanged(jwt)
                            saveJwtToStorage(jwt)
                            RegisterUserEvent.SuccessRegister
                        },
                        onFailure = {
                            RegisterUserEvent.Error(
                                ("Ошибка при регистрации. " + it.message)
                            )
                        }
                    )
                }
            }
        }
    }

    fun loginUser() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data

            viewModelScope.launch {
                if (isValidEmail(data.email) &&
                    isPasswordLongEnough(data.password)
                ) {
                    val result = loginUserUseCase(
                        email = data.email,
                        password = data.password
                    )

                    _loginEvent.value = result.fold(
                        onSuccess = { jwt ->
                            onJwtChanged(jwt)
                            saveJwtToStorage(jwt)
                            LoginUserEvent.SuccessLogin
                        },
                        onFailure = {
                            LoginUserEvent.Error(
                                ("Ошибка при входе. " + it.message)
                            )
                        }
                    )
                }
            }
        }
    }

    fun checkEmailVerification() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data

            viewModelScope.launch {
                val result = checkEmailVerificationUseCase()


                _checkEmailVerificationEvent.value = result.fold(
                    onSuccess = { verified ->
                        onEmailVerificationChanged(verified)
                        saveVerificationStatusToStorage(verified)
                        CheckEmailVerificationEvent.SuccessChecked(verified)
                    },
                    onFailure = {
                        CheckEmailVerificationEvent.Error(
                            (it.message ?: "")
                        )
                    }
                )
            }
        }
    }

    fun sendCode() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                if (!data.wasSent) {
                    val responseResult = sendVerificationCodeUseCase(data.email)

                    _sendCodeEvent.value = responseResult.fold(
                        onSuccess = { _ ->
                            onWasSentChanged(true)
                            SendVerificationCodeEvent.SuccessSend
                        },
                        onFailure = {
                            SendVerificationCodeEvent.Error(
                                (it.message ?: "")
                            )
                        }
                    )
                }
            }
        }
    }

    fun addProfilePhoto() {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                data.selectedImageUri?.let { uri ->
                    val photoResultResponse = saveUserPhotoUseCase(uri, null)
                    _savePhotoEvent.value = photoResultResponse.fold(
                        onSuccess = { _ ->
                            SavePhotoEvent.SuccessSave
                        },
                        onFailure = {
                            SavePhotoEvent.Error(it.message ?: "")
                        }
                    )
                }
            }
        }
    }

    fun verifyUser(type: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                val verificationResult = checkVerificationCodeUseCase(data.code.joinToString(""))

                _verifyCodeEvent.value = verificationResult.fold(
                    onSuccess = {
                        val verificationStatusResult = checkEmailVerificationUseCase()

                        _verifyUserEvent.value = verificationStatusResult.fold(
                            onSuccess = { status ->
                                if (status) {
                                    if (type == "login") {
                                        saveVerificationStatusToStorage(true)
                                    }
                                    VerifyUserEvent.SuccessVerify
                                } else {
                                    VerifyUserEvent.Error("Не верифицирован email")
                                }
                            },
                            onFailure = {
                                VerifyUserEvent.Error(it.message ?: "")
                            }
                        )
                        VerifyCodeEvent.SuccessVerify
                    },
                    onFailure = {
                        VerifyCodeEvent.Error(it.message ?: "")
                    }
                )
            }
        }
    }

    fun saveJwtToStorage(jwt: String) {
        viewModelScope.launch {
            dataManager.saveJwt(jwt)
        }
    }

    fun saveVerificationStatusToStorageFromState() {
        viewModelScope.launch {
            var status = false
            val currentState = _uiState.value
            if (currentState is AuthUiState.Success) {
                status = currentState.data.verificationStatus
            }
            dataManager.saveVerificationStatus(status)
        }
    }

    fun saveVerificationStatusToStorage(status: Boolean) {
        viewModelScope.launch {
            dataManager.saveVerificationStatus(status)
        }
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
        )
    }

    fun onUsernameChanged(username: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(username = username)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onEmailChanged(email: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(email = email)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onNicknameChanged(nickname: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(nickname = nickname)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onPasswordChanged(password: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(password = password)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onPasswordAgainChanged(passwordAgain: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(passwordAgain = passwordAgain)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onDigitInCodeChanged(index: Int, value: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedCode = currentState.data.code.toMutableList()
            updatedCode[index] = value
            val updatedData = currentState.data.copy(code = updatedCode)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onEmailVerificationChanged(verified: Boolean) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(verificationStatus = verified)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onWasSentChanged(wasSent: Boolean) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(wasSent = wasSent)
            _uiState.value = AuthUiState.Success(updatedData)
        }
    }

    fun onJwtChanged(jwt: String) {
        val currentState = _uiState.value
        if (currentState is AuthUiState.Success) {
            val updatedData = currentState.data.copy(jwt = jwt)
            _uiState.value = AuthUiState.Success(updatedData)
        }
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
    fun resetRegisterEvent() {
        _registerEvent.value = null
    }
    fun resetLoginEvent() {
        _loginEvent.value = null
    }
    fun resetSendCodeEvent() {
        _sendCodeEvent.value = null
    }
    fun resetVerifyCodeEvent() {
        _verifyCodeEvent.value = null
    }

    fun resetVerifyUserEvent() {
        _verifyUserEvent.value = null
    }

    fun resetCheckVerifyEmailEvent() {
        _checkEmailVerificationEvent.value = null
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

private fun isValidNickname(nickname: String): Boolean {
    return nickname.isNotEmpty()
}