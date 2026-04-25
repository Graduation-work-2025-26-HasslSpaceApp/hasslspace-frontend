package ru.hse.app.hasslspace.domain.usecase.auth

import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class CheckEmailVerificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Boolean> {
        return userRepository.checkEmailVerification()
    }
}