package ru.hse.app.androidApp.ui.components.profile.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.state.StatusCircle
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UserProfile(
    imageLoader: ImageLoader,
    userName: String,
    nickname: String,
    isDarkTheme: Boolean,
    status: StatusPresentation = StatusPresentation.ACTIVE,
    profilePictureUrl: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = profilePictureUrl,
                    imageLoader = imageLoader,
                    error = painterResource(
                        if (isDarkTheme)
                            R.drawable.avatar_default_dark
                        else
                            R.drawable.avatar_default_light
                    )
                ),
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .size(159.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            StatusCircle(
                status = status,
                size = 28.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 15.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                VariableMedium(text = userName, fontSize = 29.sp)
                Spacer(modifier = Modifier.height(4.dp))
                VariableLight(text = "@$nickname", fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreviewLight() {
    AppTheme(
        isDark = false
    ) {
        UserProfile(
            userName = "Иван",
            nickname = "ivan123_34",
            isDarkTheme = false,
            imageLoader = ImageLoader(LocalContext.current)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreviewDark() {
    AppTheme(
        isDark = true
    ) {
        UserProfile(
            userName = "Иван",
            nickname = "ivan123_34",
            isDarkTheme = true,
            imageLoader = ImageLoader(LocalContext.current)
        )
    }
}
