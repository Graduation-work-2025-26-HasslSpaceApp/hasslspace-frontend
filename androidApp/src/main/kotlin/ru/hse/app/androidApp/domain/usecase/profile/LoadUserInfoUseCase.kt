package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfo
import javax.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class LoadUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<UserExpandedInfo> {
        return userRepository.getUserInfo()
    }
}