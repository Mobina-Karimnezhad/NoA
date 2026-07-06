package com.noa.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noa.app.domain.model.WeekDay
import com.noa.app.ui.theme.Divider
import com.noa.app.ui.theme.NoATheme
import com.noa.app.ui.theme.PrimaryGreen

@Composable
fun WeekDayChip(

    day: WeekDay,

    selected: Boolean,

    onClick: () -> Unit

) {

    val backgroundColor = animateColorAsState(
        if (selected)
            PrimaryGreen.copy(alpha = 0.12f)
        else
            Color.Transparent,
        label = ""
    )

    val borderColor = animateColorAsState(
        if (selected)
            PrimaryGreen
        else
            Divider,
        label = ""
    )

    val borderWidth = animateDpAsState(
        if (selected)
            2.dp
        else
            1.dp,
        label = ""
    )

    val scale = animateFloatAsState(
        if (selected)
            1.05f
        else
            1f,
        label = ""
    )

    Box(

        modifier = Modifier
            .size(46.dp)
            .scale(scale.value)
            .background(backgroundColor.value, CircleShape)
            .border(borderWidth.value, borderColor.value, CircleShape)
            .clickable { onClick() },

        contentAlignment = Alignment.Center

    ) {

        Text(

            text = day.shortName,

            style = MaterialTheme.typography.titleMedium,

            color =
                if (selected)
                    PrimaryGreen
                else
                    MaterialTheme.colorScheme.onSurface

        )

    }

}

@Preview(showBackground = true)
@Composable
fun WeekDayChipPreview() {

    NoATheme {

        WeekDayChip(

            day = WeekDay.SAT,

            selected = true,

            onClick = {}

        )

    }

}