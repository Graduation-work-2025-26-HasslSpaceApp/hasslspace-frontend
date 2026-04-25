package ru.hse.app.hasslspace.domain.usecase.auth

import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class CheckVerificationCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        code: String,
    ): Result<String> {
        return userRepository.checkVerificationCode(code)
    }
}