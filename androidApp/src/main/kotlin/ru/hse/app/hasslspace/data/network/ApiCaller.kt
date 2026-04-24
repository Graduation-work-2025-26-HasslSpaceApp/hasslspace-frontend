package ru.hse.app.hasslspace.data.network

import retrofit2.Response
import ru.hse.app.hasslspace.data.exception.ApiException


//TODO Подумать над тем, что ошибки в домейн должны обрабатываться в UI
class ApiCaller {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(
                    ApiException(
                        response.code(),
                        response.errorBody()?.string() ?: "Ошибка при вызове бэкенда",
                        null
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(ApiException(null, e.message ?: "Ошибка при вызове бэкенда", e))
        }
    }
}