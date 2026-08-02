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

    private val habitDataSource: DefaultHabitDataSource

) : ViewModel() {

    var historyItems by mutableStateOf<List<InsightHistoryItem>>(
        emptyList()
    )
        private set

    private val suggestedHabits =
        habitDataSource.getAll()

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
                        if (userHabit != null && habit != null)
                            "${habit.title} - ${userHabit.customTitle}"
                        else
                            userHabit?.customTitle ?: ""

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

}