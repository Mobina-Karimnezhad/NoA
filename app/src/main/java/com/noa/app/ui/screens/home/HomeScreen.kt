package com.noa.app.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.data.repository.HabitRepository
import com.noa.app.data.repository.UserHabitRepository

@Composable
fun HomeScreen() {

    val userHabits = UserHabitRepository.getHabits()

    val suggestedHabits = HabitRepository().getSuggestedHabits()

    val firstHabit =
        userHabits.firstOrNull()?.let { userHabit ->

            suggestedHabits.firstOrNull {

                it.id == userHabit.habitId

            }

        }

    Column(

        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        if (firstHabit != null) {

            Text(

                text = "سلام 🌱",

                style = MaterialTheme.typography.headlineMedium

            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(

                painter = painterResource(firstHabit.imageRes),

                contentDescription = null,

                modifier = Modifier.size(180.dp)

            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(

                text = firstHabit.title,

                style = MaterialTheme.typography.headlineSmall,

                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                text = "🔥 ۰ روز از هدف",

                style = MaterialTheme.typography.bodyLarge

            )

        } else {

            Text(

                text = "هنوز عادتی ایجاد نشده است.",

                style = MaterialTheme.typography.bodyLarge

            )

        }

    }

}