package ru.hse.app.androidApp.domain.repository

interface CentrifugoRepository {

    suspend fun getCentrifugoToken(): Result<String>
}