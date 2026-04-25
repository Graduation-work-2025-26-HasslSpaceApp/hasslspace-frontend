package ru.hse.app.hasslspace.domain.usecase.channel

import ru.hse.app.hasslspace.domain.repository.ChannelRepository
import javax.inject.Inject

class AssignChannelPermissionUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        serverId: String,
        channelId: String,
        roleId: String
    ): Result<String> {
        return channelRepository.assignChannelPermission(serverId, channelId, roleId)
    }
}