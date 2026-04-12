package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.model.TokenRequest
import ru.hse.app.androidApp.data.model.toRoomTypeDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.repository.CallRepository
import javax.inject.Inject

class CallRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : CallRepository {

    override suspend fun getTokenForVoiceRoom(
        name: String,
        roomName: String, // todo вернуть не хардкод
        roomType: String,
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.getTokenForVoiceRoom(TokenRequest(
                name = name,
                roomName =  "05237F2A-7802-4AD8-B7DB-5EAA9F4FC467",//roomName,
                roomType = roomType.toRoomTypeDto()
            ))
        }
    }
}
