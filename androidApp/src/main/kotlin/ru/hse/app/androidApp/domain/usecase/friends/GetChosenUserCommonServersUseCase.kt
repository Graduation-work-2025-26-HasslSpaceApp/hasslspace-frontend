package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.repository.FriendRepository
import javax.inject.Inject

class GetChosenUserCommonServersUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(
        userId: String
    ): Result<List<ServerInfo>> {
        return friendRepository.getCommonServers(userId)
    }
}