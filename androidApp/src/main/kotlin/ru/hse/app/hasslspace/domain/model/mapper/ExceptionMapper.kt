package ru.hse.app.hasslspace.domain.model.mapper

import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.ui.errorhandling.UiError

fun mapApiExceptionToUiError(apiException: ApiException): UiError {
    return when (apiException.code) {
        401 -> UiError.UnauthorizedError()
        in 500..599 -> UiError.ServerError()
        408 -> UiError.NetworkError()
        else -> UiError.UnknownError()
    }
}
