package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class CreateTextChannelUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String, createChannel: CreateChannel): Result<String> {
        return serverRepository.createChannel(serverId, createChannel)
    }
}