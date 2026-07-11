package com.noa.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.ui.theme.Background
import com.noa.app.ui.theme.Divider
import com.noa.app.ui.theme.PrimaryGreen
import com.noa.app.ui.theme.TextSecondary

@Composable
fun HomeHabitCard(

    habit: Habit,

    userHabit: UserHabit,

    onClick: () -> Unit

) {

    val progress =
        userHabit.completedDays.toFloat() /
                userHabit.targetDays.toFloat()

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },

        shape = RoundedCornerShape(24.dp),

        border = BorderStroke(1.dp, Divider),

        colors = CardDefaults.cardColors(
            containerColor = Background.copy(alpha = .55f)
        )

    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(

                    painter = painterResource(habit.imageRes),

                    contentDescription = null,

                    modifier = Modifier.size(64.dp)

                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(

                        text = userHabit.customTitle,

                        style = MaterialTheme.typography.titleLarge,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = habit.title,

                        color = TextSecondary,

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(

                text = "🔥 ${userHabit.currentStreak} روز تداوم",

                style = MaterialTheme.typography.titleMedium

            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(

                progress = { progress },

                modifier = Modifier.fillMaxWidth(),

                color = PrimaryGreen

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "${userHabit.completedDays} / ${userHabit.targetDays} روز",

                style = MaterialTheme.typography.bodySmall

            )

        }

    }

}