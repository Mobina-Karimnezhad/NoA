package com.noa.app.ui.screens.choosehabit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noa.app.R
import com.noa.app.domain.model.Habit
import com.noa.app.ui.components.HabitCard
import com.noa.app.ui.theme.NoATheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import com.noa.app.ui.theme.CardBackground

@Composable
fun ChooseFirstHabitScreen(

    onSkip: () -> Unit,

    onContinue: (Habit) -> Unit,

    viewModel: ChooseFirstHabitViewModel = viewModel()

) {

    val habits = viewModel.habits

    var selectedHabit by remember {

        mutableStateOf<Habit?>(null)

    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        TextButton(

            modifier = Modifier.align(Alignment.End),

            onClick = onSkip

        ) {

            Text("فعلاً رد می‌کنم")

        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(

            painter = painterResource(R.drawable.illustration_first_habit),

            contentDescription = null,

            modifier = Modifier.size(260.dp)

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(

            text = "اولین قدم همیشه مهم‌ترین قدمه",

            style = MaterialTheme.typography.headlineMedium,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "یکی از عادت‌های پیشنهادی را انتخاب کن تا مسیر نو شدن را شروع کنیم",

            style = MaterialTheme.typography.bodyLarge,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedCard(

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.outlinedCardColors(
                containerColor = CardBackground
            )

        ){

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(habits) { habit ->

                    HabitCard(

                        title = habit.title,

                        selected = selectedHabit?.id == habit.id,

                        onClick = {

                            selectedHabit = habit

                        }

                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            enabled = selectedHabit != null,

            onClick = {

                selectedHabit?.let {

                    onContinue(it)

                }

            }

        ) {

            Text("شروع با این عادت")

        }

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ChooseFirstHabitPreview() {

    NoATheme {

        ChooseFirstHabitScreen(

            onSkip = {},

            onContinue = {}

        )

    }

}