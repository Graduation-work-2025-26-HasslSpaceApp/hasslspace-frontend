package ru.hse.app.androidApp.domain.usecase.channel

import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.repository.ChannelRepository
import javax.inject.Inject

class CreateChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        serverId: String,
        createChannel: CreateChannel
    ): Result<String> {
        return channelRepository.createChannel(serverId, createChannel)
    }
}