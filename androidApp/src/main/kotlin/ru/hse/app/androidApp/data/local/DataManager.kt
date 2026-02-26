package ru.hse.app.androidApp.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val _jwtFlow = MutableStateFlow(sharedPreferences.getString(JWT_KEY, null))
    val jwtFlow: StateFlow<String?> = _jwtFlow

    private val _verificationFlow =
        MutableStateFlow(sharedPreferences.getBoolean(VERIFICATION_KEY, false))
    val verificationFlow: StateFlow<Boolean> = _verificationFlow

    private val _isDark = MutableStateFlow(sharedPreferences.getBoolean(IS_DARK_THEME, false))
    val isDark: StateFlow<Boolean> = _isDark

    fun saveJwt(jwt: String) {
        sharedPreferences.edit { putString(JWT_KEY, jwt) }
        _jwtFlow.value = jwt
    }

    fun clearJwt() {
        sharedPreferences.edit { remove(JWT_KEY) }
        _jwtFlow.value = null
    }

    fun saveVerificationStatus(status: Boolean) {
        sharedPreferences.edit { putBoolean(VERIFICATION_KEY, status) }
        _verificationFlow.value = status
    }

    fun clearVerificationStatus() {
        sharedPreferences.edit { remove(VERIFICATION_KEY) }
        _verificationFlow.value = false
    }

    fun saveIsDarkTheme(isDark: Boolean) {
        sharedPreferences.edit { putBoolean(IS_DARK_THEME, isDark) }
        _isDark.value = isDark
    }

    fun clearIsDarkTheme() {
        sharedPreferences.edit { remove(IS_DARK_THEME) }
        _isDark.value = false
    }

    companion object {
        private const val JWT_KEY = "jwt_key"
        private const val VERIFICATION_KEY = "verification_key"
        private const val IS_DARK_THEME = "is_dark_theme"
    }
}