package com.noa.app.ui.screens.myperformance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPerformanceScreen(

    onBack: () -> Unit,

    viewModel: MyPerformanceViewModel = hiltViewModel()

) {

    val filteredItems = viewModel.filteredItems

    val selectedFilter = viewModel.selectedFilter

    val habitFilterOptions = viewModel.habitFilterOptions

    val isAnalyzing = viewModel.isAnalyzing

    val analysisFeedback = viewModel.analysisFeedback

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text("عملکرد من")
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

        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)

            ) {

                Button(

                    onClick = {
                        viewModel.runPersonalizedAnalysis()
                    },

                    enabled = !isAnalyzing,

                    modifier = Modifier.fillMaxWidth()

                ) {

                    if (isAnalyzing) {

                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp)
                        )

                    } else {

                        Text("تحلیل هوشمند")

                    }

                }

                if (analysisFeedback != null) {

                    Text(

                        text = analysisFeedback,

                        style = MaterialTheme.typography.bodySmall,

                        modifier = Modifier.padding(top = 8.dp)

                    )

                }

            }

            LazyRow(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),

                horizontalArrangement = Arrangement.spacedBy(8.dp),

                contentPadding = PaddingValues(bottom = 12.dp)

            ) {

                item {

                    FilterChip(

                        selected =
                            selectedFilter == MyPerformanceFilter.All,

                        onClick = {
                            viewModel.selectFilter(
                                MyPerformanceFilter.All
                            )
                        },

                        label = { Text("همه") }

                    )

                }

                item {

                    FilterChip(

                        selected =
                            selectedFilter ==
                                    MyPerformanceFilter.AiAnalysis,

                        onClick = {
                            viewModel.selectFilter(
                                MyPerformanceFilter.AiAnalysis
                            )
                        },

                        label = { Text("تحلیل‌های هوشمند") }

                    )

                }

                items(
                    items = habitFilterOptions,
                    key = { it.userHabitId }
                ) { option ->

                    FilterChip(

                        selected =
                            selectedFilter == option,

                        onClick = {
                            viewModel.selectFilter(option)
                        },

                        label = { Text(option.label) }

                    )

                }

            }

            if (filteredItems.isEmpty()) {

                Column(

                    modifier = Modifier.fillMaxSize()

                ) {

                    Text(

                        text =
                            "پیامی برای این فیلتر پیدا نشد.",

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(24.dp),

                        textAlign = TextAlign.Center

                    )

                }

            } else {

                LazyColumn(

                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),

                    contentPadding =
                        PaddingValues(vertical = 16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)

                ) {

                    items(

                        items = filteredItems,

                        key = { it.id }

                    ) { item ->

                        Card(

                            modifier =
                                Modifier.fillMaxWidth()

                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(16.dp)

                            ) {

                                Text(

                                    text = item.habitLabel,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .labelMedium,

                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                )

                                Text(

                                    text = item.title,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium

                                )

                                Text(

                                    text = item.message,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyMedium

                                )

                                Text(

                                    text = item.dateText,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .labelSmall,

                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .outline

                                )

                            }

                        }

                    }

                }

            }

        }

    }

}