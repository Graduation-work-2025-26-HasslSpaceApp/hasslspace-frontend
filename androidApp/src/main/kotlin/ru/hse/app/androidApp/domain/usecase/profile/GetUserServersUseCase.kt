package ru.hse.app.androidApp.domain.usecase.profile

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserServersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<List<ServerInfo>> {
        return userRepository.getServers()
    }
}