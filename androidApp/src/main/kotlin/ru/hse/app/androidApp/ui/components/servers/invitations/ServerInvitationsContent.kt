package ru.hse.app.androidApp.ui.components.servers.invitations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.InviteCard
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.InvitationUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun ServerInvitationsContent(
    onBackClick: () -> Unit,
    invitations: List<InvitationUiModel>,
    onInvitationClick: (InvitationUiModel) -> Unit,
    onCancelClick: (InvitationUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))
            VariableBold(
                text = "Приглашения",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(15.dp))

        if (invitations.isEmpty()) {
            NoItemsBox("Приглашений пока нет")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(invitations, key = { it.id }) { invitation ->
                InviteCard(
                    link = invitation.link,
                    expirationDate = invitation.expirationDate,
                    onInvitationClick = { onInvitationClick(invitation) },
                    onCancelClick = { onCancelClick(invitation) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServerInvitationsContentPreviewLight() {

    AppTheme(isDark = false) {
        ServerInvitationsContent(
            onBackClick = {},
            invitations = listOf(
                InvitationUiModel(
                    id = "1",
                    link = "https://asdads",
                    //userName = "Иван Иванов",
                    //avatarUrl = "https://example.com/avatar.jpg",
                    expirationDate = LocalDateTime.now().plusDays(5)
                ),
                InvitationUiModel(
                    id = "2",
                    link = "https://jkljlkjk",
//                    userName = "Марина Ландышева",
//                    avatarUrl = "https://example.com/avatar2.jpg",
                    expirationDate = LocalDateTime.now().plusDays(3)
                )
            ),
            onCancelClick = {},
            onInvitationClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ServerInvitationsContentPreviewDark() {

    AppTheme(isDark = true) {
        ServerInvitationsContent(
            onBackClick = {},
            invitations = listOf(
                InvitationUiModel(
                    id = "1",
                    link = "https://asdads",
                    expirationDate = LocalDateTime.now().plusDays(5)
                ),
                InvitationUiModel(
                    id = "2",
                    link = "https://jkljlkjk",
                    expirationDate = LocalDateTime.now().plusDays(3)
                )
            ),
            onCancelClick = {},
            onInvitationClick = {},
        )
    }
}
