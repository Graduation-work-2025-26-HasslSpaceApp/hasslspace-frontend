package ru.hse.app.androidApp.ui.components.common.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation

@Composable
fun StatusCircle(
    status: StatusPresentation,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = status.painter),
        contentDescription = status.name,
        modifier = modifier.size(size)
    )
}

@Preview(showBackground = true)
@Composable
fun StatusCirclePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        StatusCircle(StatusPresentation.ACTIVE, 32.dp)
        StatusCircle(StatusPresentation.INVISIBLE, 32.dp)
        StatusCircle(StatusPresentation.DO_NOT_DISTURB, 32.dp)
        StatusCircle(StatusPresentation.NOT_ACTIVE, 32.dp)
    }
}

