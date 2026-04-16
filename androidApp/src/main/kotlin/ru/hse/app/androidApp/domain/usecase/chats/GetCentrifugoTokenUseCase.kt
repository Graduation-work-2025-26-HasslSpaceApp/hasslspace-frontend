package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.repository.CentrifugoRepository
import javax.inject.Inject

class GetCentrifugoTokenUseCase @Inject constructor(
    private val centrifugoRepository: CentrifugoRepository
) {
    suspend operator fun invoke(): Result<String> {

        return centrifugoRepository.getCentrifugoToken()
    }
}