package com.noa.app.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noa.app.ui.theme.PrimaryGreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TodayActionButton(

    completedToday: Boolean,

    onClick: () -> Unit

) {

    Button(

        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),

        enabled = !completedToday,

        colors = ButtonDefaults.buttonColors(

            containerColor =
                if (completedToday)
                    MaterialTheme.colorScheme.surfaceVariant
                else
                    PrimaryGreen

        ),

        onClick = onClick

    ) {

        AnimatedContent(

            targetState = completedToday,

            transitionSpec = {

                fadeIn() togetherWith fadeOut()

            },

            label = ""

        ) { done ->

            Row(

                horizontalArrangement = Arrangement.Center,

                verticalAlignment = Alignment.CenterVertically

            ) {

                if (!done) {

                    Icon(

                        imageVector = Icons.Default.PlayArrow,

                        contentDescription = null

                    )

                    Text(

                        text = "امروز انجام شد؟",

                        style = MaterialTheme.typography.titleMedium

                    )

                } else {

                    Icon(

                        imageVector = Icons.Default.CheckCircle,

                        contentDescription = null

                    )

                    Text(

                        text = " \uD83C\uDF31 آفرین! امروز انجامش دادی",

                        style = MaterialTheme.typography.titleMedium

                    )

                }

            }

        }

    }

}