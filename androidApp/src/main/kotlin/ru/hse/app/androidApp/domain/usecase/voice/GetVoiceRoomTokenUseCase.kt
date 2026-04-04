package ru.hse.app.androidApp.domain.usecase.voice

import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.repository.CallRepository
import javax.inject.Inject

class GetVoiceRoomTokenUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend operator fun invoke(identity: String, name: String, roomName: String?): Result<String> {

        if (roomName == null) {
            return Result.failure(
                ApiException(null, ApiException.FRIENDSHIP_NOT_FOUND, null)
            )
        }

        return callRepository.getTokenForVoiceRoom(identity, name, roomName)
    }
}