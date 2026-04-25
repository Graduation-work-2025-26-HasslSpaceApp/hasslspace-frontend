package ru.hse.app.hasslspace.domain.usecase.invitations

import ru.hse.app.hasslspace.domain.model.entity.Invitation
import ru.hse.app.hasslspace.domain.usecase.chats.SendMessageToUserUseCase
import javax.inject.Inject

class SendServerInvitationUseCase @Inject constructor(
    private val createServerInvitationUseCase: CreateServerInvitationUseCase,
    private val sendMessageToUserUseCase: SendMessageToUserUseCase
) {
    suspend operator fun invoke(userId: String, serverId: String): Result<String> {
        val invitationResult = createServerInvitationUseCase(serverId)

        invitationResult.fold(
            onSuccess = { invitation ->
                return sendMessageToUserUseCase(userId, buildInvitationMessage(invitation))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    private fun buildInvitationMessage(
        invitation: Invitation
    ): String {
        return "Пользователь ${invitation.creatorName} приглашает вас на сервер \"${invitation.serverName}\". Чтобы присоединиться к серверу, перейдите по ссылке ${invitation.inviteUrl} или присоединитесь по коду ${invitation.code} в приложении"
    }
}