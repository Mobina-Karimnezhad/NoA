package com.noa.app.ui.screens.achievementdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.ui.screens.habitdetail.HabitWeeklyCalendar
import java.time.LocalDate
import com.noa.app.domain.helper.PersianCalendarUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementHabitDetailScreen(

    onBack: () -> Unit,

    viewModel: AchievementHabitDetailViewModel =
        hiltViewModel()

) {

    val uiState =
        viewModel.uiState

    val completionDatePersian =

        uiState.userHabit
            ?.lastCompletedDate
            ?.let { dateString ->

                runCatching {

                    LocalDate.parse(dateString)

                }.getOrNull()

            }
            ?.let {

                PersianCalendarUtils
                    .formatPersianDate(it)

            }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(
                        text = "جزئیات دستاورد"
                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = onBack

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "بازگشت"

                        )

                    }

                }

            )

        }

    ) { padding ->

        if (
            uiState.isLoading
        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center

            ) {

                Text(
                    text = "در حال بارگذاری..."
                )

            }

        } else if (
            uiState.userHabit == null
        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(
                            horizontal = 24.dp
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center

            ) {

                Text(

                    text =
                        "اطلاعات این عادت پیدا نشد.",

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    textAlign =
                        TextAlign.Center

                )

            }

        } else {

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(
                            horizontal = 24.dp
                        )
                        .navigationBarsPadding()
                        .verticalScroll(
                            rememberScrollState()
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally

            ) {

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )


                // =================================================
                // Habit Image
                // =================================================

                Card {

                    Image(

                        painter =
                            painterResource(

                                id =
                                    uiState
                                        .habit
                                        ?.imageRes
                                        ?: android.R
                                            .drawable
                                            .ic_menu_gallery

                            ),

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(
                                96.dp
                            )

                    )

                }


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )


                // =================================================
                // Habit Title
                // =================================================

                Text(

                    text =
                        uiState
                            .userHabit
                            .customTitle,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    textAlign =
                        TextAlign.Center

                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                // =================================================
                // Habit Description
                // =================================================

                Text(

                    text =
                        uiState
                            .habit
                            ?.description
                            ?: "",

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    textAlign =
                        TextAlign.Center

                )


                Spacer(
                    modifier =
                        Modifier.height(32.dp)
                )


                // =================================================
                // Basic Information
                // =================================================

                DetailItem(

                    title =
                        "هدف",

                    value =
                        "${uiState.userHabit.targetDays} روز"

                )


                DetailItem(

                    title =
                        "روزهای انتخاب‌شده",

                    value =
                        uiState
                            .userHabit
                            .selectedDays
                            .joinToString(
                                " • "
                            ) {
                                it.persianTitle
                            }

                )


                DetailItem(

                    title =
                        "یادآور",

                    value =
                        uiState
                            .userHabit
                            .reminderTime
                            .ifBlank {
                                "-"
                            }

                )


                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )


                // =================================================
                // Statistics Title
                // =================================================

                Text(

                    text =
                        "آمار عملکرد",

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    textAlign =
                        TextAlign.Start

                )


                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )


                // =================================================
                // Statistics Cards
                // =================================================

                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )

                ) {

                    AchievementStatCard(

                        title =
                            "روزهای موفق",

                        value =
                            uiState
                                .successfulDays
                                .toString(),

                        modifier =
                            Modifier
                                .weight(1f)

                    )


                    AchievementStatCard(

                        title =
                            "روزهای از دست‌رفته",

                        value =
                            uiState
                                .missedDays
                                .toString(),

                        modifier =
                            Modifier
                                .weight(1f)

                    )

                }


                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )

                ) {

                    AchievementStatCard(

                        title =
                            "پایبندی",

                        value =
                            "${uiState.adherencePercentage}%",

                        modifier =
                            Modifier
                                .weight(1f)

                    )


                    AchievementStatCard(

                        title =
                            "روزهای متوالی نهایی",

                        value =
                            "${uiState.finalStreak} روز",

                        modifier =
                            Modifier
                                .weight(1f)

                    )

                }


                Spacer(
                    modifier =
                        Modifier.height(32.dp)
                )


                // =================================================
                // Weekly Calendar Title
                // =================================================

                Text(

                    text =
                        "گزارش عملکرد",

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    textAlign =
                        TextAlign.Start

                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                // =================================================
                // Weekly Calendar
                // =================================================

                HabitWeeklyCalendar(

                    days =
                        uiState
                            .calendarDays,

                    canGoPrevious =
                        uiState
                            .canGoPrevious,

                    canGoNext =
                        uiState
                            .canGoNext,

                    onPreviousWeek =
                        viewModel::previousWeek,

                    onNextWeek =
                        viewModel::nextWeek

                )


                Spacer(
                    modifier =
                        Modifier.height(32.dp)
                )


                // =================================================
                // Completed Status
                // =================================================

                Card(

                    modifier =
                        Modifier
                            .fillMaxWidth()

                ) {

                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    16.dp
                                ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(

                            text =
                                "این عادت با موفقیت تکمیل شده است",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary,

                            textAlign =
                                TextAlign.Center

                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        Text(

                            text =
                                "تاریخ تکمیل: ${
                                    completionDatePersian ?: "-"
                                }",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            textAlign =
                                TextAlign.Center

                        )

                    }

                }


                Spacer(
                    modifier =
                        Modifier.height(32.dp)
                )

            }

        }

    }

}


// =============================================================
// Detail Item
// =============================================================

@Composable
private fun DetailItem(

    title: String,

    value: String

) {

    Column(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp
                ),

        verticalArrangement =
            Arrangement.Center

    ) {

        Text(

            text =
                title,

            style =
                MaterialTheme
                    .typography
                    .labelMedium,

            color =
                MaterialTheme
                    .colorScheme
                    .primary

        )

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        Text(

            text =
                value,

            style =
                MaterialTheme
                    .typography
                    .bodyLarge

        )

    }

}


// =============================================================
// Achievement Statistic Card
// =============================================================

@Composable
private fun AchievementStatCard(

    title: String,

    value: String,

    modifier: Modifier = Modifier

) {

    Card(

        modifier =
            modifier

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        16.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(

                text =
                    title,

                style =
                    MaterialTheme
                        .typography
                        .labelMedium,

                textAlign =
                    TextAlign.Center

            )

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            Text(

                text =
                    value,

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                textAlign =
                    TextAlign.Center

            )

        }

    }

}