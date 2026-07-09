package com.noa.app.ui.screens.celebration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.noa.app.R
import com.noa.app.ui.theme.NoATheme

@Composable
fun FirstHabitCelebrationScreen(

    onContinue: () -> Unit

) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Image(

            painter = painterResource(R.drawable.illustration_first_habit),

            contentDescription = null,

            modifier = Modifier.size(240.dp)

        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(

            text = "🎉 تبریک!",

            style = MaterialTheme.typography.headlineMedium,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(

            text = "اولین عادتت با موفقیت ساخته شد.",

            style = MaterialTheme.typography.titleLarge,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "از امروز سفر ساختن نسخه بهتر خودت شروع شد.\nهر روز فقط یک قدم جلو برو 🌱",

            style = MaterialTheme.typography.bodyLarge,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            onClick = onContinue

        ) {

            Text("شروع کنیم")

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FirstHabitCelebrationPreview() {

    NoATheme {

        FirstHabitCelebrationScreen(

            onContinue = {}

        )

    }

}