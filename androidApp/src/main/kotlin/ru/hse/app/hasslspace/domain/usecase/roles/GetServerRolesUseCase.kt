package ru.hse.app.hasslspace.domain.usecase.roles

import ru.hse.app.hasslspace.domain.model.entity.RoleInfo
import ru.hse.app.hasslspace.domain.repository.RoleRepository
import javax.inject.Inject

class GetServerRolesUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<RoleInfo>> {
        return roleRepository.getServerRoles(serverId)
    }
}