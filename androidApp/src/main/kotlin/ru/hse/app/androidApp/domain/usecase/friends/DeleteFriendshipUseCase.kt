package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.repository.FriendRepository
import javax.inject.Inject

class DeleteFriendshipUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    suspend operator fun invoke(userId: String): Result<String> {
        return friendRepository.deleteFriendship(userId)
    }
}