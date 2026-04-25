package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.CentrifugoRepository
import javax.inject.Inject

class GetCentrifugoTokenUseCase @Inject constructor(
    private val centrifugoRepository: CentrifugoRepository
) {
    suspend operator fun invoke(): Result<String> {

        return centrifugoRepository.getCentrifugoToken()
    }
}