package ru.hse.app.androidApp.domain.usecase.auth

import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
    ): Result<String> {
        return userRepository.sendVerificationCode(email)
    }
}