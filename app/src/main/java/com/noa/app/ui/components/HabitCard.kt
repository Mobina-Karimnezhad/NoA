package com.noa.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noa.app.ui.theme.PrimaryGreen
import com.noa.app.ui.theme.Surface
import androidx.compose.ui.tooling.preview.Preview
import com.noa.app.ui.theme.NoATheme
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.scale
import com.noa.app.ui.theme.Divider

@Composable
fun HabitCard(

    title: String,

    selected: Boolean,

    onClick: () -> Unit

) {

    val borderColor by animateColorAsState(

        if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outline,

        label = ""

    )

    val containerColor by animateColorAsState(

        targetValue =
            if (selected)
                PrimaryGreen.copy(alpha = 0.08f)
            else
                Surface,

        label = ""

    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.01f else 1f,
        label = "cardScale"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (selected) 2.dp else 1.dp,
        label = "borderWidth"
    )


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                onClick()
            },

        border = BorderStroke(
            width = borderWidth,
            color =
                if (selected)
                    PrimaryGreen
                else
                    Divider
        ),

        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            Text(

                text = title,

                style = MaterialTheme.typography.bodyLarge

            )

            Icon(

                imageVector =
                    if (selected)
                        Icons.Default.CheckCircle
                    else
                        Icons.Outlined.Circle,

                contentDescription = null,

                tint = borderColor

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun HabitCardPreview() {

    NoATheme {

        HabitCard(

            title = "ورزش",

            selected = true,

            onClick = {}

        )

    }

}