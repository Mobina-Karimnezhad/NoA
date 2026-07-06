package com.noa.app.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noa.app.ui.theme.Divider
import com.noa.app.ui.theme.PrimaryGreen

@Composable
fun OnboardingIndicator(
    currentPage: Int,
    pageCount: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        repeat(pageCount) { index ->

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(10.dp)
                    .background(
                        if (index == currentPage)
                            PrimaryGreen
                        else
                            Divider,
                        CircleShape
                    )
            )

        }

    }

}