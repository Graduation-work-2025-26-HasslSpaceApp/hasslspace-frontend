package ru.hse.app.androidApp.domain.usecase.roles

import ru.hse.app.androidApp.domain.repository.RoleRepository
import javax.inject.Inject

class AssignRoleUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Result<String> {
        return roleRepository.assignRole(serverId, targetUserId, roleId)
    }
}