package com.noa.app.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noa.app.R
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview
import com.noa.app.ui.theme.NoATheme
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.first
import com.noa.app.data.datastore.UserPreferences
import com.noa.app.data.datastore.UserPreferences.dataStore

@Composable
fun SplashScreen(

    onNavigateToOnboarding: () -> Unit,

    onNavigateToHome: () -> Unit,

    viewModel: SplashViewModel = viewModel()

) {

    var showLogo by remember { mutableStateOf(false) }

    var showSubtitle by remember { mutableStateOf(false) }

    val context = LocalContext.current


    LaunchedEffect(Unit) {

        delay(200)
        showLogo = true

        delay(900)
        showSubtitle = true

        delay(1200)

        val preferences = context.dataStore.data.first()

        val completed =
            preferences[
                UserPreferences.ONBOARDING_COMPLETED
            ] ?: false

        if (completed)
            onNavigateToHome()
        else
            onNavigateToOnboarding()

    }

    Surface(

        modifier = Modifier.fillMaxSize(),

        color = MaterialTheme.colorScheme.background

    ) {

        Column(

            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            AnimatedVisibility(

                visible = showLogo,

                enter =

                    fadeIn(

                        animationSpec = tween(700)

                    ) +

                            scaleIn(

                                initialScale = 0.75f,

                                animationSpec = tween(700)

                            )

            ) {

                Image(

                    painter = painterResource(R.drawable.ic_noa_logo),

                    contentDescription = null,

                    modifier = Modifier.size(240.dp)

                )

            }

            AnimatedVisibility(

                visible = showSubtitle,

                enter = fadeIn(

                    animationSpec = tween(600)

                )

            ) {

                Text(

                    text = "هر روز، یک قدم بهتر",

                    modifier = Modifier.padding(top = 28.dp),

                    style = MaterialTheme.typography.bodyLarge,

                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)

                )

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {

    NoATheme {

        SplashScreen(

            onNavigateToOnboarding = {},

            onNavigateToHome = {}

        )

    }

}