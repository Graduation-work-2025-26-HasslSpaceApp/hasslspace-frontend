package ru.hse.app.hasslspace.ui.components.call

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ScreenShare
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.livekit.android.compose.state.rememberTrackMuted
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.compose.ui.ScaleType
import io.livekit.android.compose.ui.VideoTrackView
import io.livekit.android.room.track.Track
import io.livekit.android.util.flow
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun PrimarySpeakerView(
    trackReference: TrackReference,
    modifier: Modifier = Modifier,
) {
    val isVideoMuted = rememberTrackMuted(trackRef = trackReference)
    val isSpeaking by trackReference.participant::isSpeaking.flow.collectAsState()
    val displayName = trackReference.participant.name ?: "Unknown"
    val isScreenShare = trackReference.source == Track.Source.SCREEN_SHARE
    val isMicrophoneEnabled by trackReference.participant::isMicrophoneEnabled.flow.collectAsState()

    PrimarySpeakerContent(
        displayName = displayName,
        isVideoMuted = isVideoMuted.value,
        isSpeaking = isSpeaking,
        isScreenShare = isScreenShare,
        isMicrophoneEnabled = isMicrophoneEnabled,
        modifier = modifier,
        videoContent = {
            VideoTrackView(
                trackReference = trackReference,
                modifier = Modifier.fillMaxSize(),
                scaleType = if (isScreenShare) ScaleType.FitInside else ScaleType.Fill,
            )
        },
    )
}

@Composable
private fun PrimarySpeakerContent(
    displayName: String,
    isVideoMuted: Boolean,
    isSpeaking: Boolean,
    isScreenShare: Boolean,
    isMicrophoneEnabled: Boolean,
    modifier: Modifier = Modifier,
    videoContent: @Composable () -> Unit = {},
) {
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (isSpeaking && !isScreenShare) {
                    Modifier.border(3.dp, MaterialTheme.colorScheme.secondary, shape)
                } else {
                    Modifier
                }
            ),
    ) {
        if (!isVideoMuted) {
            videoContent()
        } else {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = displayName.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        if (isScreenShare && !isVideoMuted) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ScreenShare,
                    contentDescription = "Screen share",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(16.dp),
                )
            }
        }

        if (isMicrophoneEnabled) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Microphone on",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(16.dp),
                )
            }
        } else {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.MicOff,
                    contentDescription = "Microphone off",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(16.dp),
                )
            }
        }
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
        ) {
            Text(
                text = if (isScreenShare) "$displayName • экран" else displayName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            )
        }
    }
}

@Preview(name = "Камера — активный спикер", showBackground = true, widthDp = 360, heightDp = 240)
@Composable
private fun PreviewCameraSpeaking() {
    AppTheme(
        isDark = false
    ) {
        PrimarySpeakerContent(
            displayName = "Иван Петров",
            isVideoMuted = false,
            isSpeaking = true,
            isScreenShare = false,
            isMicrophoneEnabled = true,
            modifier = Modifier.fillMaxSize(),
            videoContent = {
                Surface(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxSize(),
                ) {}
            },
        )
    }
}

@Preview(name = "Камера — активный спикер", showBackground = true, widthDp = 360, heightDp = 240)
@Composable
private fun PreviewCameraSpeaking1() {
    AppTheme(
        isDark = true
    ) {
        PrimarySpeakerContent(
            displayName = "Иван Петров",
            isVideoMuted = false,
            isSpeaking = true,
            isScreenShare = false,
            isMicrophoneEnabled = true,
            modifier = Modifier.fillMaxSize(),
            videoContent = {
                Surface(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxSize(),
                ) {}
            },
        )
    }
}

@Preview(
    name = "Камера выключена (placeholder)",
    showBackground = true,
    widthDp = 360,
    heightDp = 240
)
@Composable
private fun PreviewCameraMuted() {
    AppTheme(isDark = false) {
        PrimarySpeakerContent(
            displayName = "Анна Смирнова",
            isVideoMuted = true,
            isMicrophoneEnabled = true,
            isSpeaking = false,
            isScreenShare = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(
    name = "Камера выключена (placeholder)",
    showBackground = true,
    widthDp = 360,
    heightDp = 240
)
@Composable
private fun PreviewCameraMuted1() {
    AppTheme(isDark = true) {
        PrimarySpeakerContent(
            displayName = "Анна Смирнова",
            isVideoMuted = true,
            isMicrophoneEnabled = false,
            isSpeaking = false,
            isScreenShare = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(name = "Screen share", showBackground = true, widthDp = 360, heightDp = 240)
@Composable
private fun PreviewScreenShare() {
    AppTheme(isDark = false) {
        PrimarySpeakerContent(
            displayName = "Михаил Козлов",
            isVideoMuted = false,
            isSpeaking = false,
            isMicrophoneEnabled = true,
            isScreenShare = true,
            modifier = Modifier.fillMaxSize(),
            videoContent = {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = Modifier.fillMaxSize(),
                ) {}
            },
        )
    }
}

@Preview(name = "Screen share", showBackground = true, widthDp = 360, heightDp = 240)
@Composable
private fun PreviewScreenShare1() {
    AppTheme(isDark = true) {
        PrimarySpeakerContent(
            displayName = "Михаил Козлов",
            isVideoMuted = false,
            isSpeaking = false,
            isMicrophoneEnabled = true,
            isScreenShare = true,
            modifier = Modifier.fillMaxSize(),
            videoContent = {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = Modifier.fillMaxSize(),
                ) {}
            },
        )
    }
}

@Preview(
    name = "Dark theme — мут", showBackground = true, widthDp = 360, heightDp = 240,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewDarkMuted() {
    AppTheme(isDark = true) {
        PrimarySpeakerContent(
            displayName = "Дмитрий",
            isMicrophoneEnabled = false,
            isVideoMuted = true,
            isSpeaking = false,
            isScreenShare = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}