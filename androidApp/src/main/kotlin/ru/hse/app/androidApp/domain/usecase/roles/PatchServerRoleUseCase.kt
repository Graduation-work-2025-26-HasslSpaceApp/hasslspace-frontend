package ru.hse.app.androidApp.domain.usecase.roles

import ru.hse.app.androidApp.domain.repository.RoleRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class PatchServerRoleUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(
        serverId: String,
        roleId: String,
        name: String?,
        position: Int?,
        color: String?
    ): Result<String> {
        return roleRepository.patchRole(serverId, roleId, name, position, color)
    }
}