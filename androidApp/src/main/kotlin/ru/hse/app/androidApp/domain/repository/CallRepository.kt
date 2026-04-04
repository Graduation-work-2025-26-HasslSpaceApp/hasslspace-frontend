package ru.hse.app.androidApp.domain.repository

interface CallRepository {

    suspend fun getTokenForVoiceRoom(
        identity: String,
        name: String,
        roomName: String,
    ): Result<String>
}