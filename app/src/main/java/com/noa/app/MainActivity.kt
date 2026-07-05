package com.noa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.noa.app.navigation.NoANavGraph
import com.noa.app.ui.theme.NoATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NoATheme {

                NoANavGraph()

            }

        }

    }

}