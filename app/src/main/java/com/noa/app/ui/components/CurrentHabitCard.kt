package com.noa.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.BidiFormatter
import com.noa.app.R
import com.noa.app.ui.theme.NoATheme
import com.noa.app.ui.theme.TextSecondary

@Composable
fun CurrentHabitCard(

    title: String,

    imageRes: Int,

    streak: Int

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                MaterialTheme.colorScheme.surfaceVariant

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Image(

                painter = painterResource(imageRes),

                contentDescription = null,

                modifier = Modifier.size(150.dp)

            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = title,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(8.dp))

            val motivation = when (title) {

                "ورزش" -> "💪 وقت یه کم تحرکه"

                "مطالعه" -> "📚 چند صفحه منتظرته"

                "آب کافی" -> "💧 یه لیوان آب بنوش"

                "مدیتیشن" -> "🧘 چند دقیقه آرامش"

                "خواب منظم" -> "😴 امشب زودتر بخواب"

                else -> "🌱 امروز هم ادامه بده"

            }

            Text(

                text = motivation,

                style = MaterialTheme.typography.bodyLarge,

                color = TextSecondary,

                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(20.dp))

            val streakText = BidiFormatter.getInstance().unicodeWrap(

                "🔥 ${streak.toString()}       روز تداوم"

            )

            Text(

                text = streakText,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.SemiBold,

                textAlign = TextAlign.Center

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CurrentHabitCardPreview() {

    NoATheme {

        CurrentHabitCard(

            title = "ورزش",

            imageRes = R.drawable.habit_exercise,

            streak = 0

        )

    }

}