package ru.hse.app.hasslspace.ui.components.common.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight

@Composable
fun ProfileCard(
    type: String,
    label: String,
    cardColor: Color,
    typeColor: Color,
    labelColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {

    Card(
        modifier = modifier
            .size(width = 167.dp, height = 97.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, bottom = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            VariableLight(
                text = type,
                fontSize = 14.sp,
                fontColor = typeColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            VariableLight(
                text = label,
                fontSize = 14.sp,
                fontColor = labelColor
            )
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard(
        "Друзья",
        "123 человека",
        Color.White,
        Color.Black,
        Color.Black
    )
}
