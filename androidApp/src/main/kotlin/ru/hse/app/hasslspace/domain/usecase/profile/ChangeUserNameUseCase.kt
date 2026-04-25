package ru.hse.app.hasslspace.domain.usecase.profile

import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class ChangeUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        newName: String
    ): Result<String> {
        return userRepository.changeUserName(newName)
    }
}