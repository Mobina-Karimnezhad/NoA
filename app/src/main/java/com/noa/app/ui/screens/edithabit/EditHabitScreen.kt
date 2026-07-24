package com.noa.app.ui.screens.edithabit

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.ui.components.NumberStepper
import com.noa.app.ui.components.WeekDaySelector
import com.noa.app.ui.theme.PrimaryGreen
import androidx.compose.foundation.background

@Composable
fun EditHabitScreen(

    onFinished: () -> Unit,

    onCancel: () -> Unit,

    viewModel: EditHabitViewModel = hiltViewModel()

) {

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "ویرایش این عادت",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "اطلاعات عادتت را تغییر بده.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "دسته",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * دسته‌بندی فقط نمایش داده می‌شود
         * و کاربر نمی‌تواند آن را تغییر دهد.
         */

        uiState.habit?.let { habit ->

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .width(90.dp)

            ) {

                Image(

                    painter = painterResource(
                        id = habit.imageRes
                    ),

                    contentDescription = null,

                    modifier = Modifier
                        .size(64.dp)
                        .border(

                            width = 2.dp,

                            color =
                                MaterialTheme.colorScheme.primary,

                            shape = RoundedCornerShape(16.dp)

                        )

                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }

        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.customTitle,
            onValueChange = {
                viewModel.updateTitle(it)
            },
            label = {
                Text(
                    text = "عنوان عادت"
                )
            },
            placeholder = {
                Text(
                    text = "مثلاً: پیاده‌روی"
                )
            },
            singleLine = true,
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,

                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,

                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "تعداد روز هدف",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        NumberStepper(

            value = uiState.targetDays,

            onIncrease = {

                viewModel.increaseTargetDays()

            },

            onDecrease = {

                viewModel.decreaseTargetDays()

            }

        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "روزهای هفته",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        WeekDaySelector(

            selectedDays = uiState.selectedDays,

            onDayClick = {

                viewModel.toggleDay(it)

            }

        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "ساعت یادآوری",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = uiState.reminderTime,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(

            text = "تنظیم ساعت یادآور در نسخه‌های بعدی اضافه خواهد شد.",

            color = MaterialTheme.colorScheme.onSurfaceVariant,

            style = MaterialTheme.typography.bodyMedium

        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(

            modifier = Modifier.fillMaxWidth(),

            onClick = onCancel

        ) {

            Text("انصراف")

        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(

            modifier = Modifier.fillMaxWidth(),

            enabled =

                uiState.userHabit != null &&

                        uiState.customTitle.isNotBlank() &&

                        uiState.targetDays >= 21 &&

                        uiState.selectedDays.isNotEmpty() &&

                        !uiState.isSaving,

            onClick = {

                viewModel.saveHabit {

                    onFinished()

                }

            }

        ) {

            Text(

                if (uiState.isSaving)

                    "در حال ذخیره..."

                else

                    "ذخیره تغییرات"

            )

        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

    }

}


@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    showSystemUi = true,
    locale = "fa"
)
@Composable
private fun EditHabitScreenPreview() {

    com.noa.app.ui.theme.NoATheme {

        androidx.compose.foundation.layout.Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(horizontal = 24.dp)
                .navigationBarsPadding(),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "ویرایش این عادت",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "اطلاعات عادتت را تغییر بده.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Text(
                text = "دسته",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // دسته انتخاب‌شده؛ فقط قابل مشاهده است
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(
                        id = android.R.drawable.ic_menu_myplaces
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            width = 2.dp,
                            color = PrimaryGreen,
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "ورزش",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            OutlinedTextField(

                modifier = Modifier.fillMaxWidth(),

                value = "ورزش صبحگاهی",

                onValueChange = {},

                label = {
                    Text("عنوان عادت")
                },

                singleLine = true

            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "تعداد روز هدف",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            NumberStepper(

                value = 30,

                onIncrease = {},

                onDecrease = {}

            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "روزهای هفته",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            WeekDaySelector(

                selectedDays = listOf(

                    com.noa.app.domain.model.WeekDay.SAT,

                    com.noa.app.domain.model.WeekDay.MON,

                    com.noa.app.domain.model.WeekDay.WED

                ),

                onDayClick = {}

            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "ساعت یادآور",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "21:00",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "تنظیم ساعت یادآور در نسخه‌های بعدی اضافه خواهد شد.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            OutlinedButton(

                modifier = Modifier.fillMaxWidth(),

                onClick = {}

            ) {

                Text("انصراف")

            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {}

            ) {

                Text("ذخیره تغییرات")

            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

        }

    }

}

