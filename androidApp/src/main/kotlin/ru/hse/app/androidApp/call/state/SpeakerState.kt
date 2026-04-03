package ru.hse.app.androidApp.call.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.livekit.android.compose.state.rememberTracks
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.room.track.Track

/**
 * Возвращает TrackReference для PrimarySpeakerView.
 *
 * Приоритет:
 * 1. Вручную закреплённый трек ([pinnedTrack]) — если участник ещё в комнате
 * 2. Активный screen share
 * 3. Активный спикер с камерой
 * 4. Любой участник с камерой (fallback)
 *
 * @param pinnedTrack Трек, закреплённый пользователем вручную (или null).
 * @param onPinInvalidated Коллбек — вызывается если закреплённый участник ушёл.
 */
@Composable
fun rememberPrimaryTrack(
    pinnedTrack: TrackReference?,
    onPinInvalidated: () -> Unit,
): TrackReference? {
    val allTracks = rememberTracks(
        sources = listOf(Track.Source.CAMERA, Track.Source.SCREEN_SHARE),
        usePlaceholders = setOf(Track.Source.CAMERA),
        onlySubscribed = false,
    )

    // Проверяем что закреплённый участник всё ещё в комнате
    if (pinnedTrack != null) {
        val stillPresent = allTracks.value.any { ref ->
            ref.participant.identity == pinnedTrack.participant.identity &&
                    ref.source == pinnedTrack.source &&
                    ref.publication != null
        }
        if (!stillPresent) {
            // Участник ушёл — уведомляем и падаем на авто
            onPinInvalidated()
        } else {
            return pinnedTrack
        }
    }

    // Авто-логика: screen share > активный спикер > fallback
    return allTracks.value.firstOrNull { it.source == Track.Source.SCREEN_SHARE && it.publication != null }
        ?: allTracks.value.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null && it.participant.isSpeaking }
        ?: allTracks.value.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null }
}