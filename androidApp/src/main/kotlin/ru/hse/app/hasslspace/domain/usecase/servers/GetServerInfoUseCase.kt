package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.model.entity.ServerInfoExpanded
import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class GetServerInfoUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<ServerInfoExpanded> {
        return serverRepository.getServerInfo(serverId)
    }
}