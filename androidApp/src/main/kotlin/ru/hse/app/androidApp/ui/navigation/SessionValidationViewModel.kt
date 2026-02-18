package ru.hse.app.androidApp.ui.navigation

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.usecase.auth.CheckEmailVerificationUseCase
import ru.hse.app.androidApp.domain.usecase.auth.CheckJwtUseCase
import javax.inject.Inject

@HiltViewModel
//TODO сделать
class SessionValidationViewModel @Inject constructor(
    private val checkJwtUseCase: CheckJwtUseCase,
    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
    private val dataManager: DataManager,
    @param:ApplicationContext private val context: Context
) : ViewModel() {
    //TODO потом заменить на null
    //private val _isTokenValid = MutableStateFlow<Boolean?>(null)
    private val _isTokenValid = MutableStateFlow<Boolean?>(true)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

   //private val _isVerified = MutableStateFlow<Boolean?>(null)
    private val _isVerified = MutableStateFlow<Boolean?>(true)
    val isVerified: StateFlow<Boolean?> = _isVerified.asStateFlow()

    private val _isDark = MutableStateFlow<Boolean>(false)
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    val jwtFlow = dataManager.jwtFlow
    val verificationFlow = dataManager.verificationFlow
    val isDarkFlow = dataManager.isDark

    init {
        //TODO для теста
//        clear()

        loadTheme()

        viewModelScope.launch {
            launch {
                jwtFlow.collect { jwt ->
                    jwt?.let { checkTokenValidity() }
                        ?: _isTokenValid.update { true } //TODO исправить на false потом
                }
            }
            launch {
                verificationFlow.collect {
                    checkVerification()
                }
            }
            launch {
                isDarkFlow.collect { isDarkTheme ->
                    _isDark.update { isDarkTheme }
                }
            }
        }
    }

    private suspend fun checkTokenValidity() {
        val isValid = withContext(Dispatchers.IO) {
            val resultResponse = checkJwtUseCase()

            resultResponse.fold(
                onSuccess = { true },
                onFailure = {
                    dataManager.clearJwt()
                    false
                }
            )
        }
        _isTokenValid.update { isValid }
    }

    private suspend fun checkVerification() {
        val isVerified = withContext(Dispatchers.IO) {
            val resultResponse = checkEmailVerificationUseCase()

            resultResponse.fold(
                onSuccess = { status ->
                    if (status) {
                        dataManager.saveVerificationStatus(true)
                        true
                    } else {
                        dataManager.clearVerificationStatus()
                        false
                    }
                },
                onFailure = {
                    dataManager.clearVerificationStatus()
                    false
                }
            )
        }

        _isVerified.update { isVerified }
    }

    private fun loadTheme() {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkTheme = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> false
        }
        dataManager.saveIsDarkTheme(isDarkTheme)
        _isDark.update { isDarkTheme }
    }

    private fun clear() {
        dataManager.clearIsDarkTheme()
        dataManager.clearJwt()
        dataManager.clearVerificationStatus()
    }
}


