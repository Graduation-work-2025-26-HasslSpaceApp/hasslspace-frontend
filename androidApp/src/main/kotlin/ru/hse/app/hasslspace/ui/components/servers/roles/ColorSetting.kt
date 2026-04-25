package ru.hse.app.hasslspace.ui.components.servers.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight

@Composable
fun ColorSetting(
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onColorPickClick: () -> Unit,
) {

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        VariableLight(
            text = "Цвет", fontSize = 16.sp, modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier.clickable { onColorPickClick() },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(selectedColor, shape = CircleShape),
            )

            Icon(
                painter = painterResource(id = R.drawable.pipetka),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onColorPickClick() })

        }
    }
}