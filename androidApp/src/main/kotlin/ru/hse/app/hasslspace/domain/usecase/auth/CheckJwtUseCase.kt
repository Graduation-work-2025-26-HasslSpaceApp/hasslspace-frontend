package ru.hse.app.hasslspace.domain.usecase.auth

import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.UserRepository
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