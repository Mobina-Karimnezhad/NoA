package com.noa.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.ui.components.HomeHabitCard
import com.noa.app.ui.components.HomeHeader

@Composable
fun HomeScreen(

    onAddHabit: () -> Unit = {}

) {

    val viewModel: HomeViewModel = viewModel()

    val context = LocalContext.current

    val repository = remember {

        UserPreferencesRepository(context)

    }

    val userName by repository.userName.collectAsState(

        initial = "دوست من"

    )

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                onClick = onAddHabit

            ) {

                Icon(

                    imageVector = Icons.Default.Add,

                    contentDescription = null

                )

            }

        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            contentPadding = PaddingValues(

                top = padding.calculateTopPadding() + 16.dp,

                bottom = 96.dp

            ),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            item {

                HomeHeader(

                    userName = userName

                )

                Spacer(

                    modifier = Modifier.height(8.dp)

                )

            }

            if (viewModel.habitCards.isEmpty()) {

                item {

                    Text(

                        text = "هنوز عادتی ایجاد نشده است."

                    )

                }

            }

            items(

                viewModel.habitCards

            ) { (userHabit, habit) ->

                HomeHabitCard(

                    habit = habit,

                    userHabit = userHabit,

                    onClick = {

                        // بعداً Habit Details

                    }

                )

            }

        }

    }

}