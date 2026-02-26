package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class CreateServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(createServer: CreateServer): Result<String> {
        return serverRepository.createServer(createServer)
    }
}