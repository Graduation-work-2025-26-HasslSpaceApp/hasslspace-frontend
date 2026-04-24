package ru.hse.app.hasslspace.ui.components.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.hse.app.hasslspace.ui.components.common.button.ApplyButton
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun RowButtonDialog(
    showDialog: MutableState<Boolean>,
    questionText: String,
    apply: String,
    dismiss: String,
    onApplyClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Box(
                modifier = Modifier
                    .width(343.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(top = 25.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VariableMedium(
                        textAlign = TextAlign.Center,
                        text = questionText,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                    ) {
                        ApplyButton(
                            onClick = onApplyClick,
                            text = apply,
                            modifier = Modifier.width(135.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        ApplyButton(
                            onClick = onDismissClick,
                            text = dismiss,
                            modifier = Modifier.width(135.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceBright,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RowButtonDialogPreview() {
    AppTheme(isDark = false) {
        RowButtonDialog(
            showDialog = mutableStateOf(true),
            questionText = "Вы действительно хотите выйти из аккаунта?",
            dismiss = "Остаться",
            apply = "Выйти",
            onApplyClick = {},
            onDismissClick = {}
        )
    }
}

@Preview
@Composable
fun RowButtonDialogPreview1() {
    AppTheme(isDark = true) {
        RowButtonDialog(
            showDialog = mutableStateOf(true),
            questionText = "Вы действительно хотите выйти из аккаунта?",
            dismiss = "Остаться",
            apply = "Выйти",
            onApplyClick = {},
            onDismissClick = {}
        )
    }
}
