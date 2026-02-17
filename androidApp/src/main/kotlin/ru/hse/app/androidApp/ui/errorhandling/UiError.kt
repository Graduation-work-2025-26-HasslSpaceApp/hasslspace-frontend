package ru.hse.app.androidApp.ui.errorhandling

sealed class UiError(val message: String) {
    class UnauthorizedError(message: String = "Неавторизованный доступ") : UiError(message)
    class NetworkError(message: String = "Ошибка сети") : UiError(message)
    class ServerError(message: String = "Ошибка сервера") : UiError(message)
    class UnknownError(message: String = "Неизвестная ошибка") : UiError(message)
    class PhotoUploadError(message: String = "Ошибка загрузки фото") : UiError(message)
}
