package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetServerRolesUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<RoleInfo>> {
        return serverRepository.getServerRoles(serverId)
    }
}