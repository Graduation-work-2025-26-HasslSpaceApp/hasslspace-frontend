package ru.hse.app.androidApp.domain.usecase.channel

import ru.hse.app.androidApp.domain.model.entity.ChannelInfo
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetChannelInfoUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String, channelId: String): Result<ChannelInfo> {
        return serverRepository.getChannelInfo(serverId, channelId)
    }
}