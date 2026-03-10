package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetFriendsNotInServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<UserInfo>> {
        return serverRepository.getFriendsNotInServer(serverId)
    }
}