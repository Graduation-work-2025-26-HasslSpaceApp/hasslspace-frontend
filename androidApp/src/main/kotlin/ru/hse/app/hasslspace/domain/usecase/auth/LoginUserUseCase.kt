package ru.hse.app.hasslspace.domain.usecase.auth

import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<String> {
        return userRepository.loginUser(email, password)
    }
}