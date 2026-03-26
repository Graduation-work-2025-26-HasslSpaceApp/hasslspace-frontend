package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetServersUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(): Result<List<ServerInfo>> {
        return serverRepository.getServers()
    }
}