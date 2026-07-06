package com.noa.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.noa.app.ui.theme.Divider
import com.noa.app.ui.theme.PrimaryGreen
import androidx.compose.ui.tooling.preview.Preview
import com.noa.app.ui.theme.NoATheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.noa.app.ui.theme.Error
import com.noa.app.ui.utils.toPersianDigits
import androidx.core.text.BidiFormatter

@Composable
fun NumberStepper(

    value: Int,

    onIncrease: () -> Unit,

    onDecrease: () -> Unit

) {

    OutlinedCard(

        modifier = Modifier.fillMaxWidth(),

        border = BorderStroke(
            1.dp,
            Divider
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 10.dp
                ),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            IconButton(

                modifier = Modifier.size(42.dp),

                onClick = onDecrease

            ) {

                Icon(

                    imageVector = Icons.Default.Remove,

                    contentDescription = null,

                    tint = Error

                )

            }

            val text = BidiFormatter.getInstance().unicodeWrap(
                "${value.toPersianDigits()} روز"
            )

            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall
            )

            IconButton(

                modifier = Modifier.size(42.dp),

                onClick = onIncrease

            ) {

                Icon(

                    imageVector = Icons.Default.Add,

                    contentDescription = null,

                    tint = PrimaryGreen

                )

            }

    }

    }
}
@Preview(showBackground = true)
@Composable
fun NumberStepperPreview() {

    NoATheme {

        NumberStepper(

            value = 21,

            onIncrease = {},

            onDecrease = {}

        )

    }

}