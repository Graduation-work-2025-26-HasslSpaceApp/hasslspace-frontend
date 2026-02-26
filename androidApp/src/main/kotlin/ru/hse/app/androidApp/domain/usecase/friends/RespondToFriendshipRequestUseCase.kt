package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.repository.FriendRepository
import javax.inject.Inject

class RespondToFriendshipRequestUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(friendshipId: String, status: String): Result<String> {
        return friendRepository.respondToFriendshipRequest(friendshipId, status)
    }
}