package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.repository.FriendRepository
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(): Result<List<UserInfo>> {
        return friendRepository.getFriends() //TODO исправить когда будет отбор по статусу
    }
}