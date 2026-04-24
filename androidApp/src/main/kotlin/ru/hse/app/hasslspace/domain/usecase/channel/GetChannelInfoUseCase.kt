package ru.hse.app.hasslspace.domain.usecase.channel

import ru.hse.app.hasslspace.domain.model.entity.ChannelInfo
import ru.hse.app.hasslspace.domain.repository.ChannelRepository
import javax.inject.Inject

class GetChannelInfoUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(serverId: String, channelId: String): Result<ChannelInfo> {
        return channelRepository.getChannelInfo(serverId, channelId)
    }
}