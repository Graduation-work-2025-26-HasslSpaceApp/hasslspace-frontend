package ru.hse.app.androidApp.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.app.androidApp.data.local.DataManager
import javax.inject.Inject

@HiltViewModel
//TODO сделать
class SessionValidationViewModel @Inject constructor(
//    private val checkJwtUseCase: CheckJwtUseCase,
//    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
    private val dataManager: DataManager
) : ViewModel() {
    //TODO потом заменить на null
//    private val _isTokenValid = MutableStateFlow<Boolean?>(null)
    private val _isTokenValid = MutableStateFlow<Boolean?>(true)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

//    private val _isVerified = MutableStateFlow<Boolean?>(null)
    private val _isVerified = MutableStateFlow<Boolean?>(true)
    val isVerified: StateFlow<Boolean?> = _isVerified.asStateFlow()

    private val _isDark = MutableStateFlow<Boolean>(false)
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    val jwtFlow = dataManager.jwtFlow
    val verificationFlow = dataManager.verificationFlow
    val isDarkFlow = dataManager.isDark
//
//    init {
//        viewModelScope.launch {
//            launch {
//                jwtFlow.collect { jwt ->
//                    jwt?.let { checkTokenValidity() }
//                        ?: _isTokenValid.update { false }
//                }
//            }
//            launch {
//                verificationFlow.collect {
//                    checkVerification()
//                }
//            }
//        }
//    }
//
//    private suspend fun checkTokenValidity() {
//        val isValid = withContext(Dispatchers.IO) {
//            val resultResponse = checkJwtUseCase.execute()
//            when (resultResponse) {
//                is ApiCallResult.Success -> {
//                    val result = resultResponse.data is UserDto
//                    result
//                }
//
//                is ApiCallResult.Error -> {
//                    verificationManager.clearJwt()
//                    false
//                }
//            }
//        }
//        _isTokenValid.update { isValid }
//    }
//
//    private suspend fun checkVerification() {
//        val isVerified = withContext(Dispatchers.IO) {
//            val resultResponse = checkEmailVerificationUseCase.execute()
//            when (resultResponse) {
//                is ApiCallResult.Success -> {
//                    if (resultResponse.data) {
//                        verificationManager.saveVerificationStatus(true)
//                        true
//                    } else {
//                        verificationManager.clearVerificationStatus()
//                        false
//                    }
//
//                }
//
//                is ApiCallResult.Error -> {
//                    verificationManager.clearVerificationStatus()
//                    false
//                }
//            }
//        }
//
//        _isVerified.update { isVerified }
//    }
}


