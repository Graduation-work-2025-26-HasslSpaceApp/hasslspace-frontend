package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.model.entity.UserInfo
import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class GetFriendsNotInServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<UserInfo>> {
        return serverRepository.getFriendsNotInServer(serverId)
    }
}