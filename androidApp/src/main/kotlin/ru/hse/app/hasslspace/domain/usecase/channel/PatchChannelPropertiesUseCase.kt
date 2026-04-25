package ru.hse.app.hasslspace.domain.usecase.channel

import ru.hse.app.hasslspace.domain.repository.ChannelRepository
import javax.inject.Inject

class PatchChannelPropertiesUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        serverId: String,
        channelId: String,
        name: String?,
        position: Int?,
        maxMembers: Int?,
        isPrivate: Boolean?
    ): Result<String> {
        return channelRepository.patchChannel(
            serverId,
            channelId,
            name,
            position,
            maxMembers,
            isPrivate
        )
    }
}