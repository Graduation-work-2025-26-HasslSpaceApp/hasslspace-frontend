package ru.hse.app.androidApp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.math.pow

class RetryInterceptor(private val maxRetries: Int = 2) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var retries = 0
        var response: Response? = null

        // Запрос повторяется, если возникает ошибка 5xx
        while (retries <= maxRetries) {
            try {
                // Выполнение запроса
                val request = chain.request()
                response = chain.proceed(request)

                // Если код ответа не из диапазона 5xx, то возвращаем ответ
                if (response.isSuccessful || response.code !in 500..599) {
                    if (retries > 0) {
                        android.util.Log.i("RetryInterceptor",
                            "Request succeeded after $retries retries: ${request.url}")
                    }
                    return response
                }

                // Логируем ошибку и ретрай
                android.util.Log.w("RetryInterceptor",
                    "Request failed with code ${response.code} (${response.message}) for ${request.url}")

                // Закрываем response для повторной попытки
                response.close()

                // Если достигнут максимальный лимит попыток, возвращаем ответ
                if (retries == maxRetries) {
                    android.util.Log.e("RetryInterceptor",
                        "Max retries ($maxRetries) exceeded for ${request.url}. Last status code: ${response?.code}")
                    return response ?: throw IOException("Max retries exceeded")
                }

                // Логируем задержку и ретрай
                val delayMs = (1000 * 2.0.pow(retries.toDouble())).toLong()
                android.util.Log.d("RetryInterceptor",
                    "Retrying (${retries + 1}/$maxRetries) after ${delayMs}ms delay for ${request.url}")

                // Задержка между попытками
                Thread.sleep(delayMs)

            } catch (e: IOException) {
                // Обрабатываем исключение, если оно возникает при выполнении запроса
                android.util.Log.e("RetryInterceptor",
                    "Network error on attempt ${retries + 1}/${maxRetries + 1}: ${e.message}")

                if (retries == maxRetries) {
                    android.util.Log.e("RetryInterceptor",
                        "Max retries exceeded, throwing exception")
                    throw e
                }

                val delayMs = (1000 * 2.0.pow(retries.toDouble())).toLong()
                android.util.Log.d("RetryInterceptor",
                    "Retrying after network error (${retries + 1}/$maxRetries) after ${delayMs}ms delay")
                Thread.sleep(delayMs)
                e.printStackTrace()
            }

            retries++
        }

        // Возвращаем null, если попытки исчерпаны
        return response ?: throw IOException("Max retries exceeded")
    }
}