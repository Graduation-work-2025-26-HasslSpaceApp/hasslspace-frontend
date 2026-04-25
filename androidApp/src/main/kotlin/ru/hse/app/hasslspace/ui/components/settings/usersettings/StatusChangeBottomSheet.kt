package ru.hse.app.hasslspace.ui.components.settings.usersettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.button.ApplyButton
import ru.hse.app.hasslspace.ui.components.common.quarks.RadioButtonToggle
import ru.hse.app.hasslspace.ui.components.common.state.StatusCircle
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.entity.model.StatusPresentation
import ru.hse.app.hasslspace.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusChangeBottomSheet(
    options: List<StatusPresentation>,
    selectedOption: StatusPresentation,
    onSelectedOptionChanged: (StatusPresentation) -> Unit,
    onApply: (StatusPresentation) -> Unit,
    onDismiss: () -> Unit,
    showSortSheet: MutableState<Boolean>,
) {

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            showSortSheet.value = false
        },
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.5f),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.outline
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                VariableMedium(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Сетевой статус",
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectedOptionChanged(option) }
                        ) {
                            RadioButtonToggle(
                                isChosen = selectedOption == option,
                                onToggle = { isChosen ->
                                    if (isChosen) onSelectedOptionChanged(option)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            VariableMedium(
                                text = option.label,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            StatusCircle(
                                status = option,
                                size = 20.dp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    ApplyButton(
                        text = "Сохранить",
                        onClick = {
                            onApply(selectedOption)
                            showSortSheet.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun StatusChangeBottomSheetPreview() {
    AppTheme(isDark = false) {
        StatusChangeBottomSheet(
            selectedOption = StatusPresentation.ONLINE,
            onSelectedOptionChanged = {},
            onApply = { selected -> println("Выбрана опция: $selected") },
            showSortSheet = mutableStateOf(true),
            options = listOf(
                StatusPresentation.ONLINE,
                StatusPresentation.OFFLINE,
                StatusPresentation.DO_NOT_DISTURB,
                StatusPresentation.INVISIBLE
            ),
            onDismiss = {}
        )
    }
}

@Preview
@Composable
fun StatusChangeBottomSheetPreview1() {
    AppTheme(isDark = true) {
        StatusChangeBottomSheet(
            selectedOption = StatusPresentation.ONLINE,
            onSelectedOptionChanged = {},
            onApply = { selected -> println("Выбрана опция: $selected") },
            showSortSheet = mutableStateOf(true),
            options = listOf(
                StatusPresentation.ONLINE,
                StatusPresentation.OFFLINE,
                StatusPresentation.DO_NOT_DISTURB,
                StatusPresentation.INVISIBLE
            ),
            onDismiss = {}
        )
    }
}
