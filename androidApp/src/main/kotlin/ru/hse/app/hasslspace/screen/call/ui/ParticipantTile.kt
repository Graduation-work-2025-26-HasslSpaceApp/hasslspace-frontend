package ru.hse.app.hasslspace.screen.call.ui

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
import androidx.compose.material.icons.filled.PushPin
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
import androidx.compose.ui.unit.sp
import io.livekit.android.compose.state.rememberParticipantTrackReferences
import io.livekit.android.compose.state.rememberTrackMuted
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.compose.ui.VideoTrackView
import io.livekit.android.room.track.Track
import io.livekit.android.util.flow
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun ParticipantTile(
    trackReference: TrackReference,
    isPinned: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isVideoMuted = rememberTrackMuted(trackRef = trackReference)
    val isSpeaking by trackReference.participant::isSpeaking.flow.collectAsState()
    val displayName = trackReference.participant.name ?: "Unknown"
    val isMicrophoneEnabled by trackReference.participant::isMicrophoneEnabled.flow.collectAsState()

    // Если это тайл камеры — проверяем есть ли у участника активный screen share
    // Если это уже тайл screen share — бейдж не нужен, он и так очевиден
    val isScreenShare = trackReference.source == Track.Source.SCREEN_SHARE
    val screenShareTracks = rememberParticipantTrackReferences(
        sources = listOf(Track.Source.SCREEN_SHARE),
        passedParticipant = trackReference.participant,
        onlySubscribed = false,
    )
    val isScreenSharing = !isScreenShare && screenShareTracks.value.any { it.publication != null }

    ParticipantTileContent(
        displayName = displayName,
        isVideoMuted = isVideoMuted.value,
        isSpeaking = isSpeaking,
        isMicrophoneEnabled = isMicrophoneEnabled,
        isScreenSharing = isScreenSharing,
        isScreenShareTile = isScreenShare,
        isPinned = isPinned,
        modifier = modifier,
        videoContent = {
            VideoTrackView(
                trackReference = trackReference,
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ParticipantTileContent(
    displayName: String,
    isVideoMuted: Boolean,
    isSpeaking: Boolean,
    isMicrophoneEnabled: Boolean,
    isScreenSharing: Boolean,
    isScreenShareTile: Boolean,
    isPinned: Boolean,
    modifier: Modifier = Modifier,
    videoContent: @Composable () -> Unit = {},
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (isPinned) {
                    // Закреплённый трек — рамка primary потолще
                    Modifier.border(2.dp, MaterialTheme.colorScheme.primary, shape)
                } else if (isSpeaking) {
                    Modifier.border(2.dp, MaterialTheme.colorScheme.secondary, shape)
                } else {
                    Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape)
                }
            ),
    ) {
        // Видео или заглушка с инициалом
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
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        // Верхний левый угол — иконка микрофона (исправленная логика)
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp),
        ) {
            Icon(
                imageVector = if (isMicrophoneEnabled) Icons.Default.Mic else Icons.Default.MicOff,
                contentDescription = if (isMicrophoneEnabled) "Microphone on" else "Microphone off",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(3.dp)
                    .size(12.dp),
            )
        }

        // Верхний правый угол — screen share или pin (screen share приоритетнее)
        if (isScreenSharing) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ScreenShare,
                    contentDescription = "Screen sharing",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(3.dp)
                        .size(12.dp),
                )
            }
        } else if (isPinned) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = "Pinned",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(3.dp)
                        .size(12.dp),
                )
            }
        }

        // Нижний левый угол — имя
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(6.dp),
        ) {
            Text(
                // Для тайла screen share добавляем пометку в имени
                text = if (isScreenShareTile) "$displayName • экран" else displayName,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            )
        }
    }
}

// ─── Превью ───────────────────────────────────────────────────────────────────

@Preview(name = "Говорит", showBackground = true, widthDp = 160, heightDp = 120)
@Composable
private fun PreviewSpeaking() {
    AppTheme(isDark = false) {
        ParticipantTileContent(
            displayName = "Иван",
            isVideoMuted = false,
            isSpeaking = true,
            isMicrophoneEnabled = true,
            isScreenSharing = false,
            isScreenShareTile = false,
            isPinned = false,
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

@Preview(name = "Говорит (dark)", showBackground = true, widthDp = 160, heightDp = 120)
@Composable
private fun PreviewSpeakingDark() {
    AppTheme(isDark = true) {
        ParticipantTileContent(
            displayName = "Иван",
            isVideoMuted = false,
            isSpeaking = true,
            isMicrophoneEnabled = true,
            isScreenSharing = false,
            isScreenShareTile = false,
            isPinned = false,
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

@Preview(name = "Закреплён", showBackground = true, widthDp = 160, heightDp = 120)
@Composable
private fun PreviewPinned() {
    AppTheme(isDark = false) {
        ParticipantTileContent(
            displayName = "Анна",
            isVideoMuted = false,
            isSpeaking = false,
            isMicrophoneEnabled = true,
            isScreenSharing = false,
            isScreenShareTile = false,
            isPinned = true,
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

@Preview(name = "Screen share тайл", showBackground = true, widthDp = 160, heightDp = 120)
@Composable
private fun PreviewScreenShareTile() {
    AppTheme(isDark = false) {
        ParticipantTileContent(
            displayName = "Михаил",
            isVideoMuted = false,
            isSpeaking = false,
            isMicrophoneEnabled = true,
            isScreenSharing = true,
            isScreenShareTile = true,
            isPinned = false,
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

@Preview(name = "Видео выкл + мик выкл", showBackground = true, widthDp = 160, heightDp = 120)
@Composable
private fun PreviewMutedBoth() {
    AppTheme(isDark = false) {
        ParticipantTileContent(
            displayName = "Анна Смирнова",
            isVideoMuted = true,
            isSpeaking = false,
            isMicrophoneEnabled = false,
            isScreenSharing = false,
            isScreenShareTile = false,
            isPinned = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(
    name = "Видео выкл + мик выкл (dark)",
    showBackground = true,
    widthDp = 160,
    heightDp = 120
)
@Composable
private fun PreviewMutedBothDark() {
    AppTheme(isDark = true) {
        ParticipantTileContent(
            displayName = "Анна Смирнова",
            isVideoMuted = true,
            isSpeaking = false,
            isMicrophoneEnabled = false,
            isScreenSharing = false,
            isScreenShareTile = false,
            isPinned = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}