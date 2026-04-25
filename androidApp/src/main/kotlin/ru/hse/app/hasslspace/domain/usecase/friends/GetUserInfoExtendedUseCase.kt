package ru.hse.app.hasslspace.domain.usecase.friends

import ru.hse.app.hasslspace.domain.model.entity.UserExpandedInfoWithStatus
import ru.hse.app.hasslspace.domain.repository.FriendRepository
import javax.inject.Inject

class GetUserInfoExtendedUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(
        userId: String
    ): Result<UserExpandedInfoWithStatus> {
        return friendRepository.getUserInfoExtended(userId)
    }
}