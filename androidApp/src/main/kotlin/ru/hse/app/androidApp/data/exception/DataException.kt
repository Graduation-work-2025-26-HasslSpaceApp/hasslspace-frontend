package ru.hse.app.androidApp.data.exception

/**
 * Отвечает за ошибки при загрузке данных
 */
class DataException(
    override val message: String
) : Exception(message) {
    companion object {
        const val UNAUTHORIZED = "Неавторизованный доступ"
        const val UNRECOGNIZED = "Неизвестная ошибка"
        const val SERVER_ERROR = "Ошибка сервера или сети"
    }
}