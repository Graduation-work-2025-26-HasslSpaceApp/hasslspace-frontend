package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class CreateServerRoleUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String, createRole: CreateRole): Result<String> {
        return serverRepository.createServerRole(serverId, createRole)
    }
}