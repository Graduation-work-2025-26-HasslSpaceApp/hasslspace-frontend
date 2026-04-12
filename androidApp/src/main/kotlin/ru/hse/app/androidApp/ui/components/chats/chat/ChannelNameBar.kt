package ru.hse.app.androidApp.ui.components.chats.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.participantsLabel
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelNameBar(
    channelName: String,
    channelSubtitle: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
) {
    ChatAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                VariableMedium(
                    text = channelName,
                    fontSize = 14.sp,
                    fontColor = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(Modifier.height(5.dp))
                VariableLight(
                    text = channelSubtitle,
                    fontSize = 12.sp,
                    fontColor = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(30.dp),
                contentScale = ContentScale.Crop
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = actions,
        title = title,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            BackButton(
                modifier = Modifier.padding(start = 16.dp),
                onClick = onNavIconPressed,
                buttonSize = 30.dp,
                iconSize = 15.dp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChannelNameBarPreview() {

    AppTheme(isDark = false) {
        ChannelNameBar(
            channelName = "# composers",
            channelSubtitle = participantsLabel(count = 10),
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            onNavIconPressed = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChannelNameBarPreviewD() {

    AppTheme(isDark = true) {
        ChannelNameBar(
            channelName = "# composers",
            channelSubtitle = "subtitle",
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            onNavIconPressed = {}
        )
    }
}