package ru.hse.app.androidApp.domain.model.mapper

import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.ui.errorhandling.UiError

fun mapApiExceptionToUiError(apiException: ApiException): UiError {
    return when (apiException.code) {
        401 -> UiError.UnauthorizedError()
        in 500..599 -> UiError.ServerError()
        408 -> UiError.NetworkError()
        else -> UiError.UnknownError()
    }
}
