package com.noa.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.ui.components.HomeHabitCard
import com.noa.app.ui.components.HomeHeader
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import com.noa.app.ui.components.AppDrawer
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(

    onAddHabit: () -> Unit = {},

    onHabitClick: (Int) -> Unit,

    onProfileClick: () -> Unit = {},

    onAchievementsClick: () -> Unit = {},

    onMyPerformanceClick: () -> Unit = {},

    isDarkTheme: Boolean,

    onDarkThemeChange: (Boolean) -> Unit,

    userName: String,

    userAvatarName: String?

) {

    val viewModel: HomeViewModel = hiltViewModel()

    val drawerState =
        rememberDrawerState(
            initialValue = DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()



    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            AppDrawer(
                drawerState = drawerState,
                scope = scope,
                onProfileClick = onProfileClick,
                onAchievementsClick = {
                    onAchievementsClick()
                },
                onMyPerformanceClick = {
                    onMyPerformanceClick()
                },
                isDarkTheme = isDarkTheme,
                onDarkThemeChange = onDarkThemeChange
            )

        }

    ) {

        Scaffold(

            floatingActionButton = {

                FloatingActionButton(

                    onClick = onAddHabit

                ) {

                    Icon(

                        imageVector = Icons.Default.Add,

                        contentDescription = "افزودن عادت"

                    )

                }

            }

        ) { padding ->

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .navigationBarsPadding()

            ) {

                HomeHeader(

                    userName = userName,

                    userAvatarName = userAvatarName,

                    onMenuClick = {

                        scope.launch {

                            drawerState.open()

                        }

                    }

                )

                Spacer(

                    modifier = Modifier.height(16.dp)

                )

                if (viewModel.habitCards.isEmpty()) {

                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),

                        contentAlignment = Alignment.Center

                    ) {

                        Text(

                            text = "هنوز عادتی ایجاد نشده است.",

                            style = MaterialTheme.typography.bodyLarge

                        )

                    }

                } else {

                    LazyColumn(

                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 24.dp),

                        contentPadding = PaddingValues(

                            bottom = 96.dp

                        ),

                        verticalArrangement =
                            Arrangement.spacedBy(20.dp)

                    ) {

                        items(

                            viewModel.habitCards

                        ) { (userHabit, habit) ->

                            HomeHabitCard(

                                habit = habit,

                                userHabit = userHabit,

                                onClick = {

                                    onHabitClick(userHabit.id)

                                }

                            )

                        }

                    }

                }

            }

        }

    }

    /*
     * =========================================================
     * Weekly Insights Popup
     * =========================================================
     */

    if (viewModel.showWeeklyInsightsDialog) {

        AlertDialog(

            onDismissRequest = {

            },

            title = {

                Text(

                    text = "گزارش هفته‌ی گذشته",

                    style = MaterialTheme.typography.headlineSmall,

                    textAlign = TextAlign.Center,

                    modifier = Modifier.fillMaxWidth()

                )

            },

            text = {

                Column {

                    viewModel.weeklyInsights.forEach { insight ->

                        Text(

                            text =
                                "برای عادت «${viewModel.habitLabelFor(insight.userHabitId)}»:\n${insight.message}",

                            modifier = Modifier.padding(vertical = 6.dp)

                        )

                    }

                }

            },

            confirmButton = {

                TextButton(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel.dismissWeeklyInsightsDialog()

                    }

                ) {

                    Text(

                        text = "باشه",

                        modifier = Modifier.fillMaxWidth(),

                        textAlign = TextAlign.Center

                    )

                }

            }

        )

    }

}