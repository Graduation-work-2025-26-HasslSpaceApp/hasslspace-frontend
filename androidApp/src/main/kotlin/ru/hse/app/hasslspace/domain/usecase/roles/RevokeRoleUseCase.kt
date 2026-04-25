package ru.hse.app.hasslspace.domain.usecase.roles

import ru.hse.app.hasslspace.domain.repository.RoleRepository
import javax.inject.Inject

class RevokeRoleUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Result<String> {
        return roleRepository.revokeRole(serverId, targetUserId, roleId)
    }
}