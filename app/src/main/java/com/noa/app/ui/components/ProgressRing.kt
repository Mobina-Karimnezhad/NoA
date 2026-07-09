package com.noa.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.ui.utils.toPersianDigits
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(

    completedDays: Int,

    targetDays: Int

) {

    val progress =

        completedDays.toFloat() / targetDays.toFloat()

    val animatedProgress =

        animateFloatAsState(

            targetValue = progress,

            label = ""

        )

    Column(

        modifier = Modifier.fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        CircularProgressIndicator(

            progress = {

                animatedProgress.value

            },

            modifier = Modifier.size(120.dp),

            strokeWidth = 10.dp

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text =
                "${completedDays.toPersianDigits()} / ${targetDays.toPersianDigits()} روز",

            style = MaterialTheme.typography.titleMedium,

            textAlign = TextAlign.Center

        )

    }

}