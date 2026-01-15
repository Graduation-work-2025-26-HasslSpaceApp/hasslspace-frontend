package ru.hse.app.androidApp.ui.components.userinfocard

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette

//TODO перенести в домейн слой

fun extractMainColor(bitmap: Bitmap): Color {
    val palette = Palette.from(bitmap).generate()
    val color = palette.getVibrantColor(
        palette.getDominantColor(0xFF444444.toInt())
    )
    return Color(color).copy(alpha = 0.4f)
}
