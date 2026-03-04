package ru.hse.app.androidApp.ui.components.servers.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import ru.hse.app.androidApp.ui.components.common.button.ApplyButton
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ColorPickerBlock(
    onSaveClick: (Color) -> Unit,
    showDialog: MutableState<Boolean>,
) {
    //TODO допилить логику
    val controller = rememberColorPickerController()

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Box(
                modifier = Modifier
                    .width(370.dp)
                    .height(670.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(top = 25.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VariableMedium(
                        text = "Выберите цвет для роли",
                        fontSize = 20.sp,
                    )

                    Spacer(Modifier.height(20.dp))

                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        controller = controller,
                    )

                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                    )

                    Spacer(Modifier.height(20.dp))

                    VariableLight(
                        text = "Выбранный цвет",
                        fontSize = 16.sp,
                    )
                    Spacer(Modifier.height(30.dp))
                    AlphaTile(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(10.dp)
                            ),
                        controller = controller
                    )

                }
                ApplyButton(
                    onClick = { onSaveClick(controller.selectedColor.value) },
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

@Preview(showBackground = true)
@Composable
fun ColorPickerBlockPreview1() {
    AppTheme(isDark = false) {
        ColorPickerBlock(
            showDialog = remember { mutableStateOf(true) },
            onSaveClick = {}
        )
    }
}
