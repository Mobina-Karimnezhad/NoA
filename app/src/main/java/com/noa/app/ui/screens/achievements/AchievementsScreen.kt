package com.noa.app.ui.screens.achievements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.ui.components.HomeHabitCard
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(

    onBack: () -> Unit,

    onHabitClick: (Int) -> Unit,

    viewModel: AchievementsViewModel =
        hiltViewModel()

) {

    val uiState =
        viewModel.uiState


    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text("دستاوردهای من")
                },

                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "بازگشت"
                        )
                    }
                }

            )
        }

    ) { padding ->

        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(
                        horizontal = 24.dp
                    ),

            contentPadding =
                PaddingValues(
                    bottom = 32.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    20.dp
                )

        ) {

            item {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )

                ) {

                    AchievementSummaryCard(

                        title =
                            "عادت‌های تکمیل‌شده",

                        value =
                            uiState
                                .completedHabitsCount
                                .toString(),

                        modifier =
                            Modifier.weight(1f)

                    )


                    AchievementSummaryCard(

                        title =
                            "بیشترین توالی",

                        value =
                            "${uiState.bestStreak} روز",

                        modifier =
                            Modifier.weight(1f)

                    )

                }

            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

            }


            items(

                items =
                    uiState.completedHabits,

                key = {
                    it.userHabit.id
                }

            ) { item ->

                HomeHabitCard(

                    habit =
                        item.habit,

                    userHabit =
                        item.userHabit,

                    onClick = {

                        onHabitClick(
                            item.userHabit.id
                        )

                    }

                )

            }

        }

    }

}


@Composable
private fun AchievementSummaryCard(

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
                    .padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(

                text = title,

                style =
                    MaterialTheme.typography.bodyMedium,

                textAlign =
                    TextAlign.Center

            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(

                text = value,

                style =
                    MaterialTheme.typography.titleLarge,

                textAlign =
                    TextAlign.Center

            )

        }

    }

}