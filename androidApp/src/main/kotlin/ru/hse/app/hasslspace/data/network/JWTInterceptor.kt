package ru.hse.app.hasslspace.data.network


import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.hse.app.hasslspace.data.local.DataManager

class JWTInterceptor(
    private val dataManager: DataManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val jwt = runBlocking { dataManager.jwtFlow.value }

        val modifiedRequest = if (jwt != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $jwt")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(modifiedRequest)

        return response
    }
}
