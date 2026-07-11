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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import com.noa.app.data.repository.HabitRepository
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.noa.app.domain.model.Habit
import com.noa.app.ui.theme.PrimaryGreen
import com.noa.app.ui.components.NumberStepper
import com.noa.app.domain.model.WeekDay
import com.noa.app.ui.components.WeekDaySelector
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun AddHabitScreen(

    onSave: (
        habit: Habit,
        customTitle: String,
        targetDays: Int,
        selectedDays: List<WeekDay>,
        reminderTime: String
    ) -> Unit,

    onCancel: () -> Unit

) {

    val habits = HabitRepository().getSuggestedHabits()

    var selectedHabit by remember {

        mutableStateOf<Habit?>(null)

    }

    var customTitle by remember {

        mutableStateOf("")

    }

    var targetDays by remember {

        mutableStateOf(21)

    }

    var reminderTime by remember {

        mutableStateOf("21:00")

    }

    var selectedDays by remember {

        mutableStateOf(

            WeekDay.entries.toList()

        )

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

            items(habits) { habit ->

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                        .width(90.dp)
                        .clickable {

                            selectedHabit = habit

                        }

                ) {

                    Image(

                        painter = painterResource(habit.imageRes),

                        contentDescription = null,

                        modifier = Modifier
                            .size(64.dp)
                            .border(
                                width = if (selectedHabit?.id == habit.id) 2.dp else 0.dp,
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

            value = customTitle,

            onValueChange = {

                customTitle = it

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

            value = targetDays,

            onIncrease = {

                targetDays++

            },

            onDecrease = {

                if (targetDays > 1)

                    targetDays--

            }

        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = "روزهای هفته",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        WeekDaySelector(

            selectedDays = selectedDays,

            onDayClick = { day ->

                selectedDays =

                    if (day in selectedDays)

                        selectedDays - day

                    else

                        selectedDays + day

            }

        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = "ساعت یادآوری",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = reminderTime,

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
                if (targetDays >= 21)
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
                selectedHabit != null &&
                        customTitle.isNotBlank() &&
                        targetDays >= 21 &&
                        selectedDays.isNotEmpty(),

            onClick = {

                onSave(

                    selectedHabit!!,

                    customTitle.trim(),

                    targetDays,

                    selectedDays,

                    reminderTime

                )

            }

        ) {

            Text("ذخیره عادت")

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Preview(showBackground = true)
@Composable
fun AddHabitScreenPreview() {

    AddHabitScreen(

        onSave = { _, _, _, _, _ -> },

        onCancel = {}

    )

}