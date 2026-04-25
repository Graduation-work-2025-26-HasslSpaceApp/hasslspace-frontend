package ru.hse.app.hasslspace.domain.usecase.friends

import ru.hse.app.hasslspace.domain.model.entity.UserInfo
import ru.hse.app.hasslspace.domain.repository.FriendRepository
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(): Result<List<UserInfo>> {
        return friendRepository.getFriends()
    }
}