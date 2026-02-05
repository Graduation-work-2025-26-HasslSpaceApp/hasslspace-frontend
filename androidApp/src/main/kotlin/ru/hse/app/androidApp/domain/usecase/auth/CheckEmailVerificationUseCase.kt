package ru.hse.app.androidApp.domain.usecase.auth

import javax.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class CheckEmailVerificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Boolean> {
        return userRepository.checkEmailVerification()
    }
}