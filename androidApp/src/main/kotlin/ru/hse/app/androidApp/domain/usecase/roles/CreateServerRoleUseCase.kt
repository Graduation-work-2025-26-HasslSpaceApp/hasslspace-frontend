package ru.hse.app.androidApp.domain.usecase.roles

import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.repository.RoleRepository
import javax.inject.Inject

class CreateServerRoleUseCase @Inject constructor(
    private val roleRepository: RoleRepository
) {
    suspend operator fun invoke(serverId: String, createRole: CreateRole): Result<String> {
        return roleRepository.createServerRole(serverId, createRole)
    }
}