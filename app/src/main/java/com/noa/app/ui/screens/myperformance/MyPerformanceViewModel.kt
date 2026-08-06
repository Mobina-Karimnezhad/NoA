package com.noa.app.ui.screens.myperformance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.helper.PersianCalendarUtils
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.domain.repository.UserInsightRepository
import com.noa.app.domain.usecase.GeneratePersonalizedAnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

data class InsightHistoryItem(

    val id: Int,

    val habitLabel: String,

    val title: String,

    val message: String,

    val dateText: String

)

@HiltViewModel
class MyPerformanceViewModel @Inject constructor(

    private val insightRepository: UserInsightRepository,

    private val userHabitRepository: UserHabitRepository,

    private val habitDataSource: DefaultHabitDataSource,

    private val generatePersonalizedAnalysisUseCase: GeneratePersonalizedAnalysisUseCase

) : ViewModel() {

    var historyItems by mutableStateOf<List<InsightHistoryItem>>(
        emptyList()
    )
        private set

    private val suggestedHabits =
        habitDataSource.getAll()

    var isAnalyzing by mutableStateOf(false)
        private set

    var analysisFeedback by mutableStateOf<String?>(null)
        private set

    init {

        observeInsights()

    }

    private fun observeInsights() {

        viewModelScope.launch {

            combine(

                userHabitRepository.getAllHabits(),

                insightRepository.getInsights()

            ) { userHabits, insights ->

                insights.map { insight ->

                    val userHabit =
                        userHabits.firstOrNull {
                            it.id == insight.userHabitId
                        }

                    val habit =
                        suggestedHabits.firstOrNull {
                            it.id == userHabit?.habitId
                        }

                    val label =
                        when {

                            insight.userHabitId == null ->
                                "همه‌ی عادت‌ها"

                            userHabit != null && habit != null ->
                                "${habit.title} - ${userHabit.customTitle}"

                            else ->
                                userHabit?.customTitle ?: ""

                        }

                    val date =
                        Instant.ofEpochMilli(insight.createdAt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                    InsightHistoryItem(

                        id = insight.id,

                        habitLabel = label,

                        title = insight.title,

                        message = insight.message,

                        dateText =
                            PersianCalendarUtils
                                .formatPersianDate(date)

                    )

                }

            }.collect { items ->

                historyItems = items

            }

        }

    }

    fun runPersonalizedAnalysis() {

        isAnalyzing = true

        analysisFeedback = null

        viewModelScope.launch {

            val result =
                generatePersonalizedAnalysisUseCase()

            isAnalyzing = false

            result.fold(

                onSuccess = { count ->

                    analysisFeedback =
                        if (count == 0)
                            "برای استفاده از تحلیل هوشمند، باید حداقل یکی از عادت‌هات را ۷ بار «انجام شد» ثبت کرده باشی. چند روز دیگه دوباره امتحان کن."
                        else
                            "$count تحلیل جدید اضافه شد."

                },

                onFailure = { error ->

                    analysisFeedback =
                        error.message
                            ?: "خطایی پیش اومد. دوباره امتحان کن."

                }

            )

        }

    }

}