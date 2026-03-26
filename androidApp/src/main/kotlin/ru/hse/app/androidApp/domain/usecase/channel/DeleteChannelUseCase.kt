package ru.hse.app.androidApp.domain.usecase.channel

import ru.hse.app.androidApp.domain.repository.ChannelRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class DeleteChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(serverId: String, channelId: String): Result<String> {
        return channelRepository.deleteChannel(serverId, channelId)
    }
}