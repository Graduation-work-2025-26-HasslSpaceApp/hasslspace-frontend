package ru.hse.app.hasslspace.domain.usecase.profile

import ru.hse.app.hasslspace.domain.model.entity.UserExpandedInfo
import ru.hse.app.hasslspace.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<UserExpandedInfo> {
        return userRepository.getUserInfo()
    }
}