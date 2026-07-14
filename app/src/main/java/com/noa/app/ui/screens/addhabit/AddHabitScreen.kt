package com.noa.app.ui.screens.addhabit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import com.noa.app.ui.theme.PrimaryGreen
import com.noa.app.ui.components.NumberStepper
import com.noa.app.ui.components.WeekDaySelector
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AddHabitScreen(

    initialHabitId: Int? = null,

    onFinished: () -> Unit,

    onCancel: () -> Unit,

    viewModel: AddHabitViewModel = hiltViewModel()

) {


    LaunchedEffect(initialHabitId) {

        if (initialHabitId != null &&
            viewModel.selectedHabit == null
        ) {

            viewModel.habits.firstOrNull {
                it.id == initialHabitId
            }?.let(viewModel::selectHabit)

        }

    }


    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Top

    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(

            text = "افزودن عادت جدید",

            style = MaterialTheme.typography.headlineMedium

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = "یک دسته انتخاب کن و عادت خودت را بساز.",

            style = MaterialTheme.typography.bodyLarge

        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(

            text = "دسته",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(viewModel.habits) { habit ->

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                        .width(90.dp)
                        .then(

                            if (initialHabitId == null) {

                                Modifier.clickable {

                                    viewModel.selectHabit(habit)

                                }

                            } else {

                                Modifier

                            }

                        )

                ) {

                    Image(

                        painter = painterResource(habit.imageRes),

                        contentDescription = null,

                        modifier = Modifier
                            .size(64.dp)
                            .border(
                                width = if (viewModel.selectedHabit?.id == habit.id) 2.dp else 0.dp,
                                color = PrimaryGreen,
                                shape = RoundedCornerShape(16.dp)
                            )

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(

                        text = habit.title,

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(

            modifier = Modifier.fillMaxWidth(),

            value = viewModel.customTitle,

            onValueChange = {
                viewModel.updateTitle(it)
            },

            label = {

                Text("عنوان عادت")

            },

            placeholder = {

                Text("مثلاً: پیاده‌روی")

            },

            singleLine = true

        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = "تعداد روز هدف",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        NumberStepper(

            value = viewModel.targetDays,

            onIncrease = {

                viewModel.increaseTargetDays()

            },

            onDecrease = {

                viewModel.decreaseTargetDays()

            }

        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = "روزهای هفته",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        WeekDaySelector(

            selectedDays = viewModel.selectedDays,

            onDayClick = {

                viewModel.toggleDay(it)

            }

        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = "ساعت یادآوری",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = viewModel.reminderTime,

            style = MaterialTheme.typography.headlineSmall

        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(

            onClick = {

                // بعداً TimePicker

            }

        ) {

            Text("تغییر ساعت")

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = "حداقل ۲۱ روز برای ساخت عادت پیشنهاد می‌شود.",

            color =
                if (viewModel.targetDays >= 21)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.error,

            style = MaterialTheme.typography.bodyMedium

        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(

            modifier = Modifier.fillMaxWidth(),

            onClick = onCancel

        ) {

            Text("انصراف")

        }

        Spacer(modifier = Modifier.height(12.dp))

        androidx.compose.material3.Button(

            modifier = Modifier.fillMaxWidth(),

            enabled =
                viewModel.selectedHabit != null &&
                        viewModel.customTitle.isNotBlank() &&
                        viewModel.targetDays >= 21 &&
                        viewModel.selectedDays.isNotEmpty(),

            onClick = {

                viewModel.saveHabit {

                    onFinished()

                }

            }

        ) {

            Text("ذخیره عادت")

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}
