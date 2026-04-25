package ru.hse.app.hasslspace.domain.usecase.friends

import ru.hse.app.hasslspace.domain.model.entity.ServerInfo
import ru.hse.app.hasslspace.domain.repository.FriendRepository
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