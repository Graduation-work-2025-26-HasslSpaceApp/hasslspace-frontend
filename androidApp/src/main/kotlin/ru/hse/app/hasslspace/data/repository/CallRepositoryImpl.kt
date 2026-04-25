package ru.hse.app.hasslspace.data.repository

import ru.hse.app.hasslspace.data.model.TokenRequest
import ru.hse.app.hasslspace.data.model.toRoomTypeDto
import ru.hse.app.hasslspace.data.network.ApiCaller
import ru.hse.app.hasslspace.data.network.ApiService
import ru.hse.app.hasslspace.domain.repository.CallRepository
import javax.inject.Inject

class CallRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : CallRepository {

    override suspend fun getTokenForVoiceRoom(
        name: String,
        roomName: String,
        roomType: String,
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.getTokenForVoiceRoom(
                TokenRequest(
                    name = name,
                    roomName = roomName,
                    roomType = roomType.toRoomTypeDto()
                )
            )
        }
    }
}
