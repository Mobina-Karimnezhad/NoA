package com.noa.app.ui.screens.habitdetail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(

    onBack: () -> Unit,

    onEdit: () -> Unit,

    onDelete: () -> Unit,

    viewModel: HabitDetailViewModel = hiltViewModel()

) {

    val uiState = viewModel.uiState

    val isCompleted =
        uiState.userHabit?.isCompleted == true


    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(

                        text =
                            if (isCompleted)
                                "دستاورد عادت"
                            else
                                "جزئیات عادت"

                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = onBack

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription = "بازگشت"

                        )

                    }

                },

                actions = {

                    if (!isCompleted) {

                        IconButton(

                            onClick = onEdit

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription =
                                    "ویرایش عادت"

                            )

                        }

                        IconButton(

                            onClick = {

                                viewModel.showDeleteDialog()

                            }

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Delete,

                                contentDescription =
                                    "حذف عادت"

                            )

                        }

                    }

                },

                colors =
                    TopAppBarDefaults
                        .centerAlignedTopAppBarColors()

            )

        }

    ) { padding ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
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


            /*
             * Habit Image
             */

            Surface(

                shape =
                    MaterialTheme
                        .shapes
                        .medium

            ) {

                Image(

                    painter =
                        painterResource(

                            id =
                                uiState.habit?.imageRes
                                    ?: android.R.drawable
                                        .ic_menu_gallery

                        ),

                    contentDescription = null,

                    modifier =
                        Modifier.size(96.dp)

                )

            }


            Spacer(

                modifier =
                    Modifier.height(20.dp)

            )


            /*
             * Habit Title
             */

            Text(

                text =
                    uiState.userHabit
                        ?.customTitle
                        ?: "",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall

            )


            Spacer(

                modifier =
                    Modifier.height(8.dp)

            )


            /*
             * Habit Description
             */

            Text(

                text =
                    uiState.habit
                        ?.description
                        ?: "",

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge

            )


            Spacer(

                modifier =
                    Modifier.height(32.dp)

            )



            if (isCompleted) {

                DetailItem(

                    title =
                        "روزهای متوالی نهایی",

                    value =
                        "${uiState.userHabit?.currentStreak ?: 0} روز"

                )

            } else {

                DetailItem(

                    title =
                        "هدف",

                    value =
                        "${uiState.userHabit?.targetDays ?: 0} روز"

                )

                DetailItem(

                    title =
                        "پیشرفت",

                    value =
                        "${uiState.userHabit?.currentStreak ?: 0} / " +
                                "${uiState.userHabit?.targetDays ?: 0}"

                )

                DetailItem(

                    title =
                        "روزهای متوالی",

                    value =
                        "${uiState.userHabit?.currentStreak ?: 0} روز"

                )

            }



            if (isCompleted) {

                DetailItem(

                    title =
                        "وضعیت",

                    value =
                        "این عادت با موفقیت تکمیل شده است"

                )

            } else {

                DetailItem(

                    title =
                        "یادآور",

                    value =
                        uiState.userHabit
                            ?.reminderTime
                            ?: "-"

                )

            }


            /*
             * Selected Days
             */

            DetailItem(

                title =
                    "روزهای هفته",

                value =
                    uiState.userHabit
                        ?.selectedDays
                        ?.joinToString(" • ") {

                            it.persianTitle

                        }
                        ?: "-"

            )


            Spacer(

                modifier =
                    Modifier.height(24.dp)

            )



            Text(

                text =
                    if (isCompleted)
                        "تاریخچه عادت"
                    else
                        "گزارش هفتگی من",

                modifier =
                    Modifier.fillMaxWidth(),

                style =
                    MaterialTheme
                        .typography
                        .labelMedium,

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


            HabitWeeklyCalendar(

                days =
                    uiState.calendarDays,

                canGoPrevious =
                    uiState.canGoPrevious,

                canGoNext =
                    uiState.canGoNext,

                onPreviousWeek =
                    viewModel::previousWeek,

                onNextWeek =
                    viewModel::nextWeek

            )


            if (!isCompleted) {

                Spacer(

                    modifier =
                        Modifier.height(40.dp)

                )

                Button(

                    enabled =
                        uiState.canCompleteToday,

                    onClick = {

                        viewModel.completeToday()

                    }

                ) {

                    Text(

                        text =

                            when {

                                uiState.userHabit
                                    ?.isCompleted == true ->

                                    "این عادت تکمیل شده است"

                                uiState.userHabit
                                    ?.completedToday == true ->

                                    "امروز انجام شده"

                                !uiState.todaySelected ->

                                    "امروز در برنامه نیست"

                                else ->

                                    "امروز انجام شد"

                            }

                    )

                }

            }


            Spacer(

                modifier =
                    Modifier.height(24.dp)

            )

        }

    }


    /*
     * =========================================================
     * Delete Confirmation Dialog
     * =========================================================
     */

    if (
        !isCompleted &&
        uiState.showDeleteDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                viewModel
                    .dismissDeleteDialog()

            },

            title = {

                Text(

                    text =
                        "مطمئنی می‌خوای بیخیال این عادت بشی؟!",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge

                )

            },

            text = {

                Text(

                    text =
                        "با حذف این عادت، اطلاعات مربوط به آن از لیست عادت‌های فعال شما حذف می‌شود."

                )

            },

            confirmButton = {

                TextButton(

                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel.deleteHabit {

                            onDelete()

                        }

                    }

                ) {

                    Text(

                        text =
                            "آره، بیخیالش میشم",

                        modifier =
                            Modifier.fillMaxWidth(),

                        textAlign =
                            TextAlign.Center

                    )

                }

            },

            dismissButton = {

                TextButton(

                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel
                            .dismissDeleteDialog()

                    }

                ) {

                    Text(

                        text =
                            "نه، نگهش می‌دارم",

                        modifier =
                            Modifier.fillMaxWidth(),

                        textAlign =
                            TextAlign.Center

                    )

                }

            }

        )

    }


    /*
     * =========================================================
     * Habit Completed Congratulations Dialog
     * =========================================================
     */

    if (
        uiState.showCompleteDialog
    ) {

        AlertDialog(

            onDismissRequest = {

            },

            title = {

                Text(

                    text =
                        "تبریک می‌گم!",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    textAlign =
                        TextAlign.Center,

                    modifier =
                        Modifier.fillMaxWidth()

                )

            },

            text = {

                Text(

                    text =
                        "تو موفق شدی یک عادت خوب در خودت ایجاد کنی! 🌱\n\n" +
                                "اطلاعات و تاریخچه این عادت برای همیشه در بخش «دستاوردهای من» ذخیره میشه و هر وقت خواستی می‌تونی بهش سر بزنی و موفقیتت رو ببینی.",

                    textAlign =
                        TextAlign.Center,

                    modifier =
                        Modifier.fillMaxWidth()

                )

            },

            confirmButton = {

                TextButton(

                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel
                            .dismissCompleteDialog()

                        onBack()

                    }

                ) {

                    Text(

                        text =
                            "باشه",

                        modifier =
                            Modifier.fillMaxWidth(),

                        textAlign =
                            TextAlign.Center

                    )

                }

            }

        )

    }

}


@Composable
private fun DetailItem(

    title: String,

    value: String

) {

    Column(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),

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