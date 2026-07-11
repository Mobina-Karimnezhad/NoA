package com.noa.app.ui.screens.createhabit

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import com.noa.app.R
import com.noa.app.ui.components.NumberStepper
import com.noa.app.ui.theme.Error
import com.noa.app.ui.theme.NoATheme
import com.noa.app.ui.theme.TextSecondary
import com.noa.app.domain.model.Habit
import com.noa.app.data.repository.UserHabitRepository
import com.noa.app.domain.model.UserHabit

@Composable
fun CreateHabitScreen(

    habit: Habit,

    onSave: (UserHabit) -> Unit,

    viewModel: CreateHabitViewModel = viewModel()

) {

    val days = viewModel.days

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Image(

            painter = painterResource(habit.imageRes),

            contentDescription = null,

            modifier = Modifier.size(220.dp)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(

            text = "ساخت اولین عادت",

            style = MaterialTheme.typography.headlineMedium

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = habit.title,

            style = MaterialTheme.typography.titleLarge,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(

            text = "🎯 تعداد روزهای تداوم",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        NumberStepper(

            value = days,

            onIncrease = {

                viewModel.increaseDays()

            },

            onDecrease = {

                viewModel.decreaseDays()

            }

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "حداقل ۲۱ روز برای شکل‌گیری یک عادت پیشنهاد می‌شود",

            color =
                if (days >= 21)
                    TextSecondary
                else
                    Error,

            style = MaterialTheme.typography.bodyMedium,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.weight(1f))

        Button(

            enabled = days >= 21,

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            onClick = {

                onSave(

                    UserHabit(

                        id = 1,

                        habitId = habit.id,

                        customTitle = habit.title,

                        targetDays = days,

                        selectedDays = emptyList(),

                        reminderTime = "21:00",

                        currentStreak = 0,

                        completedDays = 0

                    )

                )

            }

        ) {

            Text("ذخیره و شروع")

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CreateHabitPreview() {

    NoATheme {

        CreateHabitScreen(

            habit = Habit(

                id = 1,

                title = "ورزش",

                description = "هر روز کمی فعال‌تر باش",

                imageRes = R.drawable.habit_exercise

            ),

            onSave = {}

        )

    }

}