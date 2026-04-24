package ru.hse.app.hasslspace.domain.usecase.friends

import ru.hse.app.hasslspace.domain.repository.FriendRepository
import javax.inject.Inject

class CreateFriendRequestUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(username: String): Result<String> {
        return friendRepository.createFriendRequest(username)
    }
}