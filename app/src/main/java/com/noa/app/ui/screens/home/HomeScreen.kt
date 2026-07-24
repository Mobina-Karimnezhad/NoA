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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.ui.components.HomeHabitCard
import com.noa.app.ui.components.HomeHeader
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

    onProfileClick: () -> Unit = {}

) {

    val viewModel: HomeViewModel = hiltViewModel()

    val drawerState =
        rememberDrawerState(
            initialValue = DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()

    val context = LocalContext.current

    val repository = remember {

        UserPreferencesRepository(context)

    }

    val userName by repository.userName.collectAsState(

        initial = "دوست من"

    )

    val userAvatarName by repository.userAvatar.collectAsState(

        initial = null

    )


    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            AppDrawer(

                drawerState = drawerState,

                scope = scope,

                onProfileClick = onProfileClick

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

}