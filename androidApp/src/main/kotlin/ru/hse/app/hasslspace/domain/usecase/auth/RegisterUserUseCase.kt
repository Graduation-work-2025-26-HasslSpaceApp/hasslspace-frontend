package ru.hse.app.hasslspace.domain.usecase.auth

import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        username: String,
        nickname: String
    ): Result<String> {
        return userRepository.registerUser(email, password, username, nickname)
    }
}