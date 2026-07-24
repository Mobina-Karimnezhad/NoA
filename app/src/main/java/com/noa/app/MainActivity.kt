package com.noa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection
import com.noa.app.navigation.NoANavGraph
import com.noa.app.ui.theme.NoATheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.ui.main.MainViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

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
                            mainViewModel

                    )

                }

            }

        }

    }

}