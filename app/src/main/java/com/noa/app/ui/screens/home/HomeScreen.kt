package com.noa.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noa.app.ui.components.CurrentHabitCard
import com.noa.app.ui.components.HomeHeader
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noa.app.ui.components.TodayActionButton
import com.noa.app.ui.components.ProgressRing
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import com.noa.app.ui.components.MotivationCard
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.noa.app.data.datastore.UserPreferencesRepository
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = viewModel()

    val context = LocalContext.current

    val repository = remember {

        UserPreferencesRepository(context)

    }

    val userName by repository.userName.collectAsState(

        initial = "دوست من"

    )

    val firstHabit = viewModel.currentHabit

    val currentUserHabit = viewModel.currentUserHabit

    val completedToday = viewModel.completedToday

    val showSuccess = viewModel.showSuccessMessage

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        HomeHeader(

            userName = userName

        )

        Spacer(modifier = Modifier.height(24.dp))

        if (firstHabit != null) {

            CurrentHabitCard(

                title = firstHabit.title,

                imageRes = firstHabit.imageRes,

                streak = currentUserHabit?.currentStreak ?: 0

            )

            Spacer(modifier = Modifier.height(20.dp))

            TodayActionButton(

                completedToday = completedToday,

                onClick = {

                    viewModel.completeToday()

                }

            )

            Spacer(modifier = Modifier.height(32.dp))

            ProgressRing(

                completedDays = currentUserHabit?.completedDays ?: 0,

                targetDays = currentUserHabit?.targetDays ?: 21

            )

            Spacer(modifier = Modifier.height(28.dp))

            MotivationCard(

                text = viewModel.motivation.text

            )

            Spacer(modifier = Modifier.height(20.dp))

            if (showSuccess) {

                Text(

                    text = "✨ آفرین!\nیک قدم دیگر به ساختن نسخه بهتر خودت نزدیک شدی.",

                    style = MaterialTheme.typography.bodyLarge,

                    textAlign = TextAlign.Center

                )

            }

        } else {

            Text(

                text = "هنوز عادتی ایجاد نشده است.",

                style = MaterialTheme.typography.bodyLarge

            )

        }

    }

}