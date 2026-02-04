package ru.hse.app.androidApp.domain.usecase.auth

import jakarta.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class CheckEmailVerificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<String> {
        return userRepository.checkEmailVerification()
    }
}