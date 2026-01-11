package ru.hse.app.androidApp.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.SettingsButton
import ru.hse.app.androidApp.ui.components.common.state.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ProfileScreenContent(
    imageLoader: ImageLoader,
    username: String,
    nickname: String,
    status: StatusPresentation,
    profilePictureUrl: String,
    friendsCount: Int,
    onFriendsClick: () -> Unit,
    serversCount: Int,
    onServersClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isDarkTheme: Boolean,

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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                SettingsButton(onClick = onSettingsClick, isDarkTheme = isDarkTheme)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserProfile(
                    imageLoader = imageLoader,
                    userName = username,
                    nickname = nickname,
                    profilePictureUrl = profilePictureUrl,
                    isDarkTheme = isDarkTheme,
                    status = status
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                FriendsCard(
                    count = friendsCount,
                    modifier = Modifier.clickable { onFriendsClick() }
                )

                Spacer(modifier = Modifier.width(15.dp))
                ServersCard(
                    count = serversCount,
                    modifier = Modifier.clickable { onServersClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Profile Light")
@Composable
fun ProfileScreenContentPreviewLight() {
    AppTheme(isDark = false) {
        ProfileScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            username = "Ваше имя",
            nickname = "nickname123",
            status = StatusPresentation.ACTIVE,
            profilePictureUrl = "",
            friendsCount = 55,
            onFriendsClick = {},
            serversCount = 20,
            onServersClick = {},
            onSettingsClick = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Profile Dark")
@Composable
fun ProfileScreenContentPreviewDark() {
    AppTheme(isDark = true) {
        ProfileScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            username = "Ваше имя",
            nickname = "nickname123",
            status = StatusPresentation.DONOTDISTURB,
            profilePictureUrl = "",
            friendsCount = 1,
            onFriendsClick = {},
            serversCount = 3,
            onServersClick = {},
            onSettingsClick = {},
            isDarkTheme = true
        )
    }
}
