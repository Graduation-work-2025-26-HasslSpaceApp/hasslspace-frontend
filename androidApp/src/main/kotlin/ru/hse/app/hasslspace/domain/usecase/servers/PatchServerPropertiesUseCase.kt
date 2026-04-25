package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class PatchServerPropertiesUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(
        serverId: String,
        name: String?,
        photoUrl: String?
    ): Result<String> {
        return serverRepository.patchServer(serverId, name, photoUrl)
    }
}