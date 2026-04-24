package ru.hse.app.hasslspace.domain.usecase.channel

import ru.hse.app.hasslspace.domain.model.entity.RoleInfo
import ru.hse.app.hasslspace.domain.repository.ChannelRepository
import javax.inject.Inject

class GetChannelPermissionsUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(serverId: String, channelId: String): Result<List<RoleInfo>> {
        return channelRepository.getChannelPermissions(serverId, channelId)
    }
}