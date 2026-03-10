package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.Invitation
import java.time.LocalDateTime

@Immutable
data class InvitationUiModel(
    val id: String,
    val link: String,
    val expirationDate: LocalDateTime
)

fun Invitation.toUi(): InvitationUiModel {
    return InvitationUiModel(
        id = this.id,
        link = this.link,
        expirationDate = this.expTime
    )
}