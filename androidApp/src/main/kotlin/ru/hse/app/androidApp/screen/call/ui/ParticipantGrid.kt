package ru.hse.app.androidApp.screen.call.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.livekit.android.compose.state.rememberTracks
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.room.track.Track

@Composable
fun ParticipantGrid(
    pinnedTrack: TrackReference?,
    onTrackSelected: (TrackReference) -> Unit,
    modifier: Modifier = Modifier,
) {
    val allTracks = rememberTracks(
        sources = listOf(Track.Source.CAMERA, Track.Source.SCREEN_SHARE),
        usePlaceholders = setOf(Track.Source.CAMERA),
        onlySubscribed = false,
    )

    val tiles: List<TrackReference> = buildList {
        val cameraTracks = allTracks.value.filter { it.source == Track.Source.CAMERA }
        val screenShareTracks = allTracks.value.filter {
            it.source == Track.Source.SCREEN_SHARE && it.publication != null
        }

        for (cameraTrack in cameraTracks) {
            add(cameraTrack)
            val screenShare = screenShareTracks.firstOrNull { screenRef ->
                screenRef.participant.identity == cameraTrack.participant.identity
            }
            if (screenShare != null) {
                add(screenShare)
            }
        }
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
    ) {
        items(
            items = tiles,
            key = { ref -> "${ref.participant.identity?.value}_${ref.source}" },
        ) { trackRef ->
            ParticipantTile(
                trackReference = trackRef,
                isPinned = pinnedTrack?.let {
                    it.participant.identity == trackRef.participant.identity &&
                            it.source == trackRef.source
                } ?: false,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(3f / 4f)
                    .clickable { onTrackSelected(trackRef) },
            )
        }
    }
}