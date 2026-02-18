package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfo
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import javax.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class ChangeUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        newName: String
    ): Result<String> {
        return userRepository.changeUserName(newName)
    }
}