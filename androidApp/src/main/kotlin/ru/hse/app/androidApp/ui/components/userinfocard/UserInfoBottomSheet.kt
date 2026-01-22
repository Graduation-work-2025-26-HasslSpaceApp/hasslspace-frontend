package ru.hse.app.androidApp.ui.components.userinfocard

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.toBitmap
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.state.StatusCircle
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBottomSheet(
    isDarkTheme: Boolean,
    profilePictureUrl: String,
    imageLoader: ImageLoader,
    status: StatusPresentation,
    username: String,
    nickname: String,
    commonServersCount: Int,
    onCommonServersClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCallClick: () -> Unit,
    onVideoCallClick: () -> Unit,
    aboutUserInfo: String,
    onDismiss: () -> Unit,
    onInvite: () -> Unit,
    onCopyNickname: () -> Unit,
    onThirdOptionClick: () -> Unit,
    //TODO параметр, определяющий статус принять/Отправить заявку/ удалить из друзей
) {

    val headerColor = remember { mutableStateOf(Color.Transparent) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.outline
            )
        },
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                headerColor.value,
                            ),
                        )
                    )
                    .align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(
                        if (isDarkTheme)
                            R.drawable.three_points_dark
                        else
                            R.drawable.three_points_light
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(21.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(20.dp))

                Box {
                    AsyncImage(
                        model = profilePictureUrl,
                        imageLoader = imageLoader,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape),

                        onSuccess = { success ->
                            var bitmap = success.result.image.toBitmap()
                            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)

                            headerColor.value = extractMainColor(bitmap)
                        },

                        error = painterResource(
                            if (isDarkTheme)
                                R.drawable.avatar_default_dark
                            else
                                R.drawable.avatar_default_light
                        )
                    )


                    StatusCircle(
                        status = status,
                        size = 14.dp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 0.dp, y = 5.dp)
                    )
                }

                VariableMedium(
                    text = username,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                VariableLight(
                    text = "@$nickname",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (commonServersCount > 0) {
                    Spacer(Modifier.height(5.dp))
                    VariableLight(
                        text = commonServersLabel(commonServersCount),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickable(onClick = onCommonServersClick)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    IconTextButton(
                        text = "Сообщение",
                        iconResource = R.drawable.option_message,
                        onClick = onMessageClick
                    )
                    IconTextButton(
                        text = "Звонок",
                        iconResource = R.drawable.option_call,
                        onClick = onCallClick
                    )
                    IconTextButton(
                        text = "Видеозвонок",
                        iconResource = R.drawable.option_videocall,
                        onClick = onVideoCallClick
                    )
                }

                if (aboutUserInfo.isNotEmpty()) {
                    Spacer(Modifier.height(10.dp))
                    UserDescription(
                        text = aboutUserInfo
                    )
                }
            }

        }


    }
}

fun commonServersLabel(count: Int): String {
    val mod10 = count % 10
    val mod100 = count % 100

    return when {
        mod100 in 11..14 -> "$count общих серверов"
        mod10 == 1 -> "$count общий сервер"
        mod10 in 2..4 -> "$count общих сервера"
        else -> "$count общих серверов"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Light Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetLightPreview() {
    AppTheme(isDark = false) {
        UserInfoBottomSheet(
            isDarkTheme = false,
            profilePictureUrl = "",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Light Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetLightPreviewWithPhoto() {
    AppTheme(isDark = false) {
        UserInfoBottomSheet(
            isDarkTheme = false,
            profilePictureUrl = "https://opis-cdn.tinkoffjournal.ru/mercury/kak-koshka-manipuliruet-ludmi-1.gtqjybqvotxy..png",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Light Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetLightPreviewWithPhoto2() {
    AppTheme(isDark = false) {
        UserInfoBottomSheet(
            isDarkTheme = false,
            profilePictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfw-8fO_Jd9wLwRy-y9PZ5TOC03tD8DMm9KQ&s",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Dark Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetDarkPreview() {
    AppTheme(isDark = true) {
        UserInfoBottomSheet(
            isDarkTheme = true,
            profilePictureUrl = "",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Light Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetDarkPreviewWithPhoto() {
    AppTheme(isDark = true) {
        UserInfoBottomSheet(
            isDarkTheme = true,
            profilePictureUrl = "https://opis-cdn.tinkoffjournal.ru/mercury/kak-koshka-manipuliruet-ludmi-1.gtqjybqvotxy..png",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Light Theme",
    showBackground = true,
)
@Composable
fun UserInfoBottomSheetDarkPreviewWithPhoto2() {
    AppTheme(isDark = true) {
        UserInfoBottomSheet(
            isDarkTheme = true,
            profilePictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfw-8fO_Jd9wLwRy-y9PZ5TOC03tD8DMm9KQ&s",
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            status = StatusPresentation.ACTIVE,
            username = "Юлия Кухтина",
            nickname = "julia_dev",
            commonServersCount = 3,
            onCommonServersClick = {},
            onMessageClick = {},
            onCallClick = {},
            onVideoCallClick = {},
            aboutUserInfo = "Android разработчик. Люблю Jetpack Compose, дизайн и красивые интерфейсы.",
            onDismiss = {},
            onInvite = {},
            onCopyNickname = {},
            onThirdOptionClick = {}
        )
    }
}



