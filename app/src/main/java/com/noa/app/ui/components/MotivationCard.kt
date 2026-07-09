package com.noa.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.ui.theme.Background
import com.noa.app.ui.theme.TextSecondary

@Composable
fun MotivationCard(

    text: String

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = Background.copy(alpha = 0.55f)

        ),

        shape = RoundedCornerShape(24.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Icon(

                imageVector = Icons.Outlined.FormatQuote,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                text = text,

                style = MaterialTheme.typography.bodyLarge,

                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "✨ جمله امروز",

                style = MaterialTheme.typography.labelMedium,

                color = TextSecondary

            )

        }

    }

}