package ru.hse.app.androidApp.domain.service.common

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import javax.inject.Inject

class ColorService @Inject constructor() {
    fun extractMainColor(bitmap: Bitmap): Color {
        val palette = Palette.from(bitmap).generate()
        val color = palette.getVibrantColor(
            palette.getDominantColor(0xFF444444.toInt())
        )
        return Color(color).copy(alpha = 0.4f)
    }
}