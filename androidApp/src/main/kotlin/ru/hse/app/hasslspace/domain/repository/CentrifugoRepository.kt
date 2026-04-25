package ru.hse.app.hasslspace.domain.repository

interface CentrifugoRepository {

    suspend fun getCentrifugoToken(): Result<String>
}