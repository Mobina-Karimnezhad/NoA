package com.noa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection
import com.noa.app.navigation.NoANavGraph
import com.noa.app.ui.theme.NoATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            CompositionLocalProvider(

                LocalLayoutDirection provides LayoutDirection.Rtl

            ) {

                NoATheme {

                    NoANavGraph()

                }

            }

        }

    }

}