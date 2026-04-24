package ru.hse.app.hasslspace.ui.components.auth.photoloading

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.button.BigButton
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun AddPhotoScreenContent(
    selectedImageUri: Uri?,
    onPickImageClick: () -> Unit,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.background_up_dark else R.drawable.background_up_light),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .offset(y = (-10).dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            AddPhotoHeader()

            Spacer(modifier = Modifier.height(80.dp))

            PhotoPicker(
                imageUri = selectedImageUri,
                onClick = onPickImageClick
            )

            Spacer(modifier = Modifier.height(80.dp))

            BigButton(
                text = "Продолжить",
                onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.scrim
                )
            )

            Spacer(Modifier.height(10.dp))

            SkipText(onClick = onSkipClick)
        }
    }
}

@Preview(showBackground = true, name = "Light - Empty")
@Composable
fun AddPhotoScreenContentPreviewLightEmpty() {
    AppTheme(isDark = false) {
        AddPhotoScreenContent(
            selectedImageUri = null,
            onPickImageClick = {},
            onContinueClick = {},
            onSkipClick = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Dark - Empty")
@Composable
fun AddPhotoScreenContentPreviewDarkEmpty() {
    AppTheme(isDark = true) {
        AddPhotoScreenContent(
            selectedImageUri = null,
            onPickImageClick = {},
            onContinueClick = {},
            onSkipClick = {},
            isDarkTheme = true
        )
    }
}

@Preview(showBackground = true, name = "Light - With Image")
@Composable
fun AddPhotoScreenContentPreviewLightWithImage() {
    AppTheme(isDark = false) {
        AddPhotoScreenContent(
            selectedImageUri = "https://opis-cdn.tinkoffjournal.ru/mercury/kak-koshka-manipuliruet-ludmi-1.gtqjybqvotxy..png".toUri(),
            onPickImageClick = {},
            onContinueClick = {},
            onSkipClick = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Dark - With Image")
@Composable
fun AddPhotoScreenContentPreviewDarkWithImage() {
    AppTheme(isDark = true) {
        AddPhotoScreenContent(
            selectedImageUri = "https://opis-cdn.tinkoffjournal.ru/mercury/kak-koshka-manipuliruet-ludmi-1.gtqjybqvotxy..png".toUri(),
            onPickImageClick = {},
            onContinueClick = {},
            onSkipClick = {},
            isDarkTheme = true
        )
    }
}






