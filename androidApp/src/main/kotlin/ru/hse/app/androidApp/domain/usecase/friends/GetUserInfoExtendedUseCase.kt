package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfoWithStatus
import ru.hse.app.androidApp.domain.repository.FriendRepository
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