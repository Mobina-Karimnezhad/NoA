package com.noa.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.R
import com.noa.app.ui.theme.Background
import com.noa.app.ui.theme.NoATheme
import com.noa.app.ui.theme.TextSecondary
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeHeader(

    userName: String? = null

) {

    val greeting =
        if (userName.isNullOrBlank())
            "سلام دوست من 🌱"
        else
            "سلام $userName 🌱"

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Background.copy(alpha = 0.55f))
            .padding(horizontal = 18.dp, vertical = 14.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Image(

            painter = painterResource(R.drawable.leaf_background),

            contentDescription = null,

            modifier = Modifier.size(64.dp),

        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(

            modifier = Modifier.weight(1f),

            horizontalAlignment = Alignment.Start

        ) {

            Text(

                text = greeting,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Start

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = "امروز هم یک قدم بهتر از دیروز باش.",

                style = MaterialTheme.typography.bodyMedium,

                color = TextSecondary,

                textAlign = TextAlign.Start

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {

    NoATheme {

        HomeHeader()

    }

}