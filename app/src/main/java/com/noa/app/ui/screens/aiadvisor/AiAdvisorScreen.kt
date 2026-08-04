package com.noa.app.ui.screens.aiadvisor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.domain.model.WeekDay

private val focusAreaOptions =
    listOf(
        "سلامتی جسمی",
        "یادگیری و مطالعه",
        "آرامش ذهنی",
        "خواب بهتر"
    )

private val occupationOptions =
    listOf(
        "دانش‌آموز/دانشجو",
        "شاغل",
        "خانه‌دار",
        "بدون اشتغال"
    )

private val ageGroupOptions =
    listOf(
        "کودک",
        "نوجوان",
        "جوان",
        "بزرگسال",
        "میانسال",
        "کهنسال"
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAdvisorScreen(

    onBack: () -> Unit,

    onSuggestionAccepted: (Int) -> Unit,

    viewModel: AiAdvisorViewModel = hiltViewModel()

) {

    val uiState = viewModel.uiState

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text("مشاور هوشمند من")
                },

                navigationIcon = {

                    IconButton(onClick = onBack) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "بازگشت"
                        )

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)

        ) {

            Text(
                text = "در چه زمینه‌ای می‌خوای پیشرفت کنی؟",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            focusAreaOptions.forEach { option ->

                FilterChip(
                    selected = uiState.focusArea == option,
                    onClick = { viewModel.selectFocusArea(option) },
                    label = { Text(option) },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "وضعیت شغلی/تحصیلی‌ات چیه؟",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            occupationOptions.forEach { option ->

                FilterChip(
                    selected = uiState.occupation == option,
                    onClick = { viewModel.selectOccupation(option) },
                    label = { Text(option) },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "گروه سنی‌ات چیه؟",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            ageGroupOptions.forEach { option ->

                FilterChip(
                    selected = uiState.ageGroup == option,
                    onClick = { viewModel.selectAgeGroup(option) },
                    label = { Text(option) },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "چه روزهایی از هفته سرت شلوغ‌تره؟ (اختیاری)",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                WeekDay.entries.take(4).forEach { day ->

                    FilterChip(
                        selected = day in uiState.busyDays,
                        onClick = { viewModel.toggleBusyDay(day) },
                        label = { Text(day.persianTitle) }
                    )

                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                WeekDay.entries.drop(4).forEach { day ->

                    FilterChip(
                        selected = day in uiState.busyDays,
                        onClick = { viewModel.toggleBusyDay(day) },
                        label = { Text(day.persianTitle) }
                    )

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "توضیحات تکمیلی (اختیاری، ولی هرچی دقیق‌تر بنویسی، پیشنهاد بهتری می‌گیری)",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(

                value = uiState.extraDetails,

                onValueChange = { viewModel.updateExtraDetails(it) },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("مثلاً: دوست دارم صبح‌ها ورزش کنم، زانو درد دارم، تازه شروع کردم و می‌خوام آسون شروع کنم...")
                },

                minLines = 3,

                maxLines = 5

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${uiState.extraDetails.length}/${AiAdvisorViewModel.MAX_EXTRA_DETAILS_LENGTH}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(

                onClick = { viewModel.generateSuggestion() },

                enabled = !uiState.isLoading,

                modifier = Modifier.fillMaxWidth()

            ) {

                if (uiState.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp)
                    )

                } else {

                    Text("دریافت پیشنهاد")

                }

            }

            if (uiState.errorMessage != null) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )

            }

            if (uiState.suggestion != null) {

                val categoryTitle =
                    viewModel.habits
                        .firstOrNull {
                            it.id == uiState.suggestion.habitId
                        }
                        ?.title
                        ?: ""

                Spacer(modifier = Modifier.height(24.dp))

                Card(modifier = Modifier.fillMaxWidth()) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "پیشنهاد دستیار هوشمند",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("دسته: $categoryTitle")

                        Text(
                            "عنوان: ${uiState.suggestion.customTitle}"
                        )

                        Text(
                            "تعداد روز هدف: ${uiState.suggestion.targetDays}"
                        )

                        Text(
                            "روزهای هفته: " +
                                    uiState.suggestion.selectedDays
                                        .joinToString(", ") {
                                            it.persianTitle
                                        }
                        )

                        Text(
                            "ساعت یادآور پیشنهادی: ${uiState.suggestion.reminderTime}"
                        )

                        if (uiState.suggestion.reason.isNotBlank()) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "چرا این پیشنهاد؟",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(uiState.suggestion.reason)

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {
                                    viewModel
                                        .acceptSuggestion()
                                        ?.let(onSuggestionAccepted)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("افزودن این عادت")
                            }

                            OutlinedButton(
                                onClick = { viewModel.generateSuggestion() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("پیشنهاد دیگه")
                            }

                        }

                    }

                }

            }

        }

    }

}