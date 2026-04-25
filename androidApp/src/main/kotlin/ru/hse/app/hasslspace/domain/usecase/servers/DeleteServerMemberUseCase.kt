package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class DeleteServerMemberUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String, memberId: String): Result<String> {
        return serverRepository.deleteServerMember(serverId, memberId)
    }
}