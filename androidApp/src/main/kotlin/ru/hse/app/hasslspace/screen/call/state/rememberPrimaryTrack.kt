package ru.hse.app.hasslspace.screen.call.state

import androidx.compose.runtime.Composable
import io.livekit.android.compose.state.rememberTracks
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.room.participant.Participant
import io.livekit.android.room.track.Track

@Composable
fun rememberPrimaryTrack(
    pinnedTrack: TrackReference?,
    onPinInvalidated: () -> Unit,
    currentUserIdentity: Participant.Identity?
): TrackReference? {
    val allTracks = rememberTracks(
        sources = listOf(Track.Source.CAMERA, Track.Source.SCREEN_SHARE),
        usePlaceholders = setOf(Track.Source.CAMERA),
        onlySubscribed = false,
    )

    if (pinnedTrack != null) {
        val stillPresent = allTracks.value.any { ref ->
            ref.participant.identity == pinnedTrack.participant.identity &&
                    ref.source == pinnedTrack.source &&
                    ref.publication != null
        }
        if (!stillPresent) {
            onPinInvalidated()
        } else {
            return pinnedTrack
        }
    }

    val otherTracks = allTracks.value.filter { it.participant.identity != currentUserIdentity }

    return otherTracks.firstOrNull { it.source == Track.Source.SCREEN_SHARE && it.publication != null }
        ?: otherTracks.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null && it.participant.isSpeaking }
        ?: otherTracks.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null }
        ?: allTracks.value.firstOrNull { it.source == Track.Source.SCREEN_SHARE && it.publication != null }
        ?: allTracks.value.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null && it.participant.isSpeaking }
        ?: allTracks.value.firstOrNull { it.source == Track.Source.CAMERA && it.publication != null }
}