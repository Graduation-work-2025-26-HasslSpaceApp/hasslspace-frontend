package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.repository.CallRepository
import javax.inject.Inject

class CallRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : CallRepository {

    override suspend fun getTokenForVoiceRoom(
        identity: String,
        name: String,
        roomName: String
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.getTokenForVoiceRoom(identity, name, roomName)
        }
    }

}
