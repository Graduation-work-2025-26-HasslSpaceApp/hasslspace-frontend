package ru.hse.app.androidApp.data.network

import retrofit2.Response
import ru.hse.app.androidApp.data.exception.ApiException


//TODO Подумать над тем, что ошибки в домейн должны обрабатываться в UI
class ApiCaller {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(ApiException(response.code(), response.message(), null))
            }
        } catch (e: Exception) {
            Result.failure(ApiException(null, e.message?:"", e))
        }
    }
}