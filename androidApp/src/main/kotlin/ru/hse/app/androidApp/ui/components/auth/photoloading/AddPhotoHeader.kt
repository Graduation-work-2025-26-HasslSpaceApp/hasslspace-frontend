package ru.hse.app.androidApp.ui.components.auth.photoloading

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableBold

@Composable
fun AddPhotoHeader() {
    VariableBold(
        text = "Добавьте фотографию профиля",
        fontSize = 35.sp,
        textAlign = TextAlign.Center
    )
}
