package ru.hse.app.androidApp.domain.usecase.auth

import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class CheckJwtUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<String> {
        val userInfoResponse = userRepository.getUserInfo()

        if (userInfoResponse.isFailure) {
            return Result.failure(
                userInfoResponse.exceptionOrNull() ?: ApiException(
                    null,
                    ApiException.UNRECOGNIZED,
                    null
                )
            )
        }

        return Result.success("")
    }
}