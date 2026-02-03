package ru.hse.app.androidApp.ui.components.auth.photoloading

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PhotoPicker(
    imageUri: Uri?,
    onClick: () -> Unit,
    sizeCircle: Dp = 300.dp,
    sizeIcon: Dp = 70.dp,
    border: Dp = 2.dp
) {
    Box(
        modifier = Modifier
            .size(sizeCircle)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
            .border(border, MaterialTheme.colorScheme.surface, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить фото",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(sizeIcon)
            )
        } else {
            AsyncImage(
                model = imageUri,
                contentDescription = "Выбранное изображение",
                modifier = Modifier
                    .size(sizeCircle)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}
