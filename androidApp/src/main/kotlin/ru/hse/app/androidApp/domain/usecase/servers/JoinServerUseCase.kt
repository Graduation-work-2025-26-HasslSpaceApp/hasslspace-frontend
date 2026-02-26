package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class JoinServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    //todo
    suspend operator fun invoke(): Result<String> {
        return serverRepository.joinServer()
    }
}