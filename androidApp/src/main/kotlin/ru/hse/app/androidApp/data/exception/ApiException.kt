package ru.hse.app.androidApp.data.exception

/**
 * Отвечает за ошибки при загрузке данных
 */
class ApiException(
    val code: Int?,
    override val message: String,
    override val cause: Throwable?,
) : Exception(message) {
    companion object {
        const val UNAUTHORIZED = "Неавторизованный доступ"
        const val UNRECOGNIZED = "Неизвестная ошибка"
        const val SERVER_ERROR = "Ошибка сервера или сети"

        const val PHOTO_UPLOADING_ERROR = "Ошибка загрузки фото"

        const val FRIENDSHIP_NOT_FOUND = "Комната не найдена"
    }
}