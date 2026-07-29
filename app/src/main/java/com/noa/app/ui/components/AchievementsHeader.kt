package com.noa.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AchievementsHeader(

    onBackClick: () -> Unit

) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(

                MaterialTheme
                    .colorScheme
                    .primaryContainer

            )

    ) {

        // Back Button
        IconButton(

            onClick =
                onBackClick,

            modifier =
                Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .padding(
                        start = 10.dp
                    )

        ) {

            Icon(

                imageVector =
                    Icons.Default.ArrowBack,

                contentDescription =
                    "بازگشت",

                modifier =
                    Modifier,

                tint =
                    MaterialTheme
                        .colorScheme
                        .onPrimaryContainer

            )

        }


        // Title
        Text(

            text =
                "دستاوردهای من",

            modifier =
                Modifier
                    .align(
                        Alignment.Center
                    )
                    .padding(
                        horizontal = 60.dp
                    ),

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold,

            textAlign =
                TextAlign.Center,

            color =
                MaterialTheme
                    .colorScheme
                    .onPrimaryContainer

        )

    }

}