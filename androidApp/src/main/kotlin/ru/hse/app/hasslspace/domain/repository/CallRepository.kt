package ru.hse.app.hasslspace.domain.repository

interface CallRepository {

    suspend fun getTokenForVoiceRoom(
        name: String,
        roomName: String,
        roomType: String,
    ): Result<String>
}