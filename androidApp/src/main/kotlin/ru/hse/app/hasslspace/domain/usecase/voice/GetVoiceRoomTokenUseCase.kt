package ru.hse.app.hasslspace.domain.usecase.voice

import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.CallRepository
import javax.inject.Inject

class GetVoiceRoomTokenUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend operator fun invoke(
        name: String,
        roomName: String?,
        roomType: String
    ): Result<String> {

        if (roomName == null) {
            return Result.failure(
                ApiException(null, ApiException.ROOM_NOT_FOUND, null)
            )
        }

        return callRepository.getTokenForVoiceRoom(name, roomName, roomType)
    }
}