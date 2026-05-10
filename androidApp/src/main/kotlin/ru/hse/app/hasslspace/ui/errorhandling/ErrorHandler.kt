package ru.hse.app.hasslspace.ui.errorhandling

import android.content.Context
import android.util.Log
import io.appmetrica.analytics.AppMetrica
import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val context: Context,
) {
    private companion object {
        const val TAG = "ErrorHandler"
    }

    fun handleInfo(message: String) {
        Log.i(TAG, "Info: $message")
        showInfo(message)
    }

    fun handleError(message: String, exception: Throwable?) {
        Log.e(TAG, "Error occurred: $message")
        Log.e(TAG, "Error exception: $exception")
        showInfo(message)

        val reportException: Exception? = when (exception) {
            null -> null
            is ApiException -> {
                val code = exception.code
                if (code != null && code in 500..599) exception else null
            }

            is Exception -> exception
            else -> Exception(
                message.ifBlank { "Unknown error occurred" },
                exception
            )
        }

        if (reportException != null) {
            val fullMessage = buildString {
                append("Message: ")
                append(message.ifBlank { "Unknown error occurred" })
                append(" | ErrorMessage: ")
                append(exception?.message ?: "null")
                append(" | Exception: ")
                append(reportException.javaClass.simpleName)
            }

            AppMetrica.reportError(fullMessage, reportException)
        }

    }

    private fun showInfo(message: String) {
        val toastManager = ToastManager(context)

        toastManager.showToast(message)
    }
}