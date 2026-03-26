package ru.hse.app.androidApp.domain.usecase.roles

import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.repository.RoleRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetServerRolesUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<RoleInfo>> {
        return roleRepository.getServerRoles(serverId)
    }
}