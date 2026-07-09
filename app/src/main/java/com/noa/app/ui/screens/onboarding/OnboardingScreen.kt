package com.noa.app.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.R
import com.noa.app.ui.theme.NoATheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import kotlinx.coroutines.launch
import com.noa.app.ui.theme.PrimaryGreen
import com.noa.app.ui.theme.Divider
import androidx.lifecycle.viewmodel.compose.viewModel



private val onboardingPages = listOf(

    OnboardingPage(
        image = R.drawable.onboarding_growth,
        title = "به نوآ خوش اومدی",
        description = "هر تغییر بزرگ، از یک قدم کوچک شروع می‌شود"
    ),

    OnboardingPage(
        image = R.drawable.onboarding_habits,
        title = "عادت‌ها را ساده بساز",
        description = "برای خودت عادت تعریف کن، پیشرفتت را ثبت کن و هر روز بهتر از دیروز باش."
    ),

    OnboardingPage(
        image = R.drawable.onboarding_ai,
        title = "همراه هوشمند تو",
        description = "دستیار هوشمند نوآ بر اساس عملکردت پیشنهادهای شخصی ارائه می‌دهد."
    )

)


@Composable
fun OnboardingScreen(

    onSkip: () -> Unit,

    onFinish: () -> Unit,

    viewModel: OnboardingViewModel = viewModel()

) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { onboardingPages.size }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End

        ) {
            TextButton(

                onClick = {

                    viewModel.finishOnboarding {

                        onSkip()

                    }

                }

            ) {
                Text("رد کردن")
            }
        }

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState
        ) { page ->

            val currentPage = onboardingPages[page]

            OnboardingContent(currentPage)

        }

        OnboardingIndicator(
            currentPage = pagerState.currentPage,
            pageCount = onboardingPages.size
        )

        Spacer(modifier = Modifier.height(24.dp))

        OnboardingBottomBar(
            isLastPage = pagerState.currentPage == onboardingPages.lastIndex,
            onClick = {

                if (pagerState.currentPage == onboardingPages.lastIndex) {

                    viewModel.finishOnboarding {

                        onFinish()

                    }

                }
                else
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }

            }
        )

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun OnboardingPreview() {

    NoATheme {

        OnboardingScreen(
            onSkip = {},
            onFinish = {}
        )

    }

}