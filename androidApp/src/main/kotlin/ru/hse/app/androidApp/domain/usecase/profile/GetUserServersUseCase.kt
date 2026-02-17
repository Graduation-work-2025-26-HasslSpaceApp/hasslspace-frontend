package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfo
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import javax.inject.Inject
import ru.hse.app.androidApp.domain.repository.UserRepository

class GetUserServersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<List<ServerInfo>> {
        return userRepository.getServers()
    }
}