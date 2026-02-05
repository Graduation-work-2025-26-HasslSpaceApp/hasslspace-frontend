package ru.hse.app.androidApp.domain.usecase.auth

import javax.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class CheckVerificationCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        code: String,
    ): Result<String> {
        return userRepository.checkVerificationCode(code)
    }
}