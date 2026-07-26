package com.noa.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.navigation.NoANavGraph
import com.noa.app.ui.main.MainViewModel
import com.noa.app.ui.theme.NoATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        val notificationHabitId =
            intent.getIntExtra(
                "extra_habit_id",
                -1
            ).takeIf {
                it != -1
            }

        setContent {

            val mainViewModel:
                    MainViewModel =
                hiltViewModel()

            val isDarkTheme by
            mainViewModel
                .isDarkTheme
                .collectAsState()

            val userName by
            mainViewModel
                .userName
                .collectAsState()

            val userAvatarName by
            mainViewModel
                .userAvatarName
                .collectAsState()


            CompositionLocalProvider(

                LocalLayoutDirection
                        provides
                        LayoutDirection.Rtl

            ) {

                NoATheme(

                    darkTheme =
                        isDarkTheme

                ) {

                    NoANavGraph(

                        mainViewModel =
                            mainViewModel,

                        notificationHabitId =
                            notificationHabitId

                    )

                }

            }

        }

    }

}