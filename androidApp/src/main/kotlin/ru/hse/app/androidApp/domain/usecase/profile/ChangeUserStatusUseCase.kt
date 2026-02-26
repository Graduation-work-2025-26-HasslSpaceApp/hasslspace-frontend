package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class ChangeUserStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        newStatus: String
    ): Result<String> {
        return userRepository.changeUserStatus(newStatus)
    }
}