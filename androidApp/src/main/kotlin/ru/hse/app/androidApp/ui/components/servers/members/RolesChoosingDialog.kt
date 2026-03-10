package ru.hse.app.androidApp.ui.components.servers.members

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.hse.app.androidApp.ui.components.common.button.ApplyButton
import ru.hse.app.androidApp.ui.components.common.card.RoleCardCheckbox
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun RolesChoosingDialog(
    showDialog: MutableState<Boolean>,
    roles: List<RoleMiniCheckboxUiModel>,
    onToggleRole: (RoleMiniCheckboxUiModel) -> Unit,
    onApplyClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismissClick() }) {
            Box(
                modifier = Modifier
                    .width(343.dp)
                    .height(500.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(top = 25.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp)
                ) {
                    VariableMedium(
                        textAlign = TextAlign.Center,
                        text = "Выбери роли для участника",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(roles, key = { it.id }) { role ->
                            RoleCardCheckbox(
                                height = 50.dp,
                                title = role.title,
                                color = role.color,
                                onClick = { onToggleRole(role) },
                                isChosen = role.isChosen
                            )
                        }
                    }
                }
                ApplyButton(
                    onClick = onApplyClick,
                    text = "Сохранить",
                    modifier = Modifier
                        .width(135.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }
    }
}

private val roles = listOf(
    RoleMiniCheckboxUiModel("13", "role 1", Color.Blue, true),
    RoleMiniCheckboxUiModel("14", "role 2", Color.Red, false),
    RoleMiniCheckboxUiModel("15", "role 3", Color.Magenta, true),
    RoleMiniCheckboxUiModel("16", "role 4", Color.Yellow, true),
    RoleMiniCheckboxUiModel("3", "role 1", Color.Blue, false),
    RoleMiniCheckboxUiModel("4", "role 2", Color.Red, true),
    RoleMiniCheckboxUiModel("5", "role 3", Color.Magenta, false),
    RoleMiniCheckboxUiModel("6", "role 4", Color.Yellow, true),
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun RolesChoosingDialogPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        RolesChoosingDialog(
            showDialog = mutableStateOf(true),
            roles = roles,
            onToggleRole = {},
            onApplyClick = {},
            onDismissClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun RolesChoosingDialogPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        RolesChoosingDialog(
            showDialog = mutableStateOf(true),
            roles = roles,
            onToggleRole = {},
            onApplyClick = {},
            onDismissClick = {}
        )
    }
}