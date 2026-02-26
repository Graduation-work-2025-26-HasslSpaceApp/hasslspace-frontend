package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class ChangeUserDescUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        newDesc: String
    ): Result<String> {
        return userRepository.changeUserDesc(newDesc)
    }
}