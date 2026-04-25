package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class DeleteServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<String> {
        return serverRepository.deleteServer(serverId)
    }
}