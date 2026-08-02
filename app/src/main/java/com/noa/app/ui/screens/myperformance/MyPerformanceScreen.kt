package com.noa.app.ui.screens.myperformance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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

    val historyItems =
        viewModel.historyItems

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

        if (historyItems.isEmpty()) {

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)

            ) {

                Text(

                    text =
                        "هنوز هیچ پیامی برات ثبت نشده.",

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
                        .padding(padding)
                        .padding(horizontal = 24.dp),

                contentPadding =
                    PaddingValues(vertical = 16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                items(

                    items = historyItems,

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