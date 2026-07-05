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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.noa.app.R
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview  //**************** new
import com.noa.app.ui.theme.NoATheme    //***************** new

@Composable
fun SplashScreen(
    onNavigate: () -> Unit
) {

    var showLogo by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        delay(200)
        showLogo = true

        delay(900)
        showSubtitle = true

        delay(1200)
        onNavigate()
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
                    contentDescription = "NoA Logo",
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SplashScreenPreview() {

    NoATheme {

        SplashScreen(
            onNavigate = {}
        )

    }

}