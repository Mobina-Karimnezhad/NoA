package com.noa.app.ui.screens.habitdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.repository.UserHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.noa.app.domain.usecase.CompleteHabitUseCase
import com.noa.app.domain.model.WeekDay
import java.util.Calendar

@HiltViewModel
class HabitDetailViewModel @Inject constructor(

    private val repository: UserHabitRepository,

    private val habitDataSource: DefaultHabitDataSource,

    private val completeHabitUseCase: CompleteHabitUseCase,

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val habitId: Int =
        savedStateHandle
            .get<String>("habitId")
            ?.toInt()
            ?: error("Habit id not found")

    var uiState by mutableStateOf(
        HabitDetailUiState()
    )
        private set

    init {

        observeHabit()

    }

    private fun observeHabit() {

        viewModelScope.launch {

            repository
                .getHabitById(habitId)
                .collectLatest { userHabit ->

                    val habit =
                        habitDataSource
                            .getAll()
                            .firstOrNull {

                                it.id == userHabit?.habitId

                            }

                    uiState = uiState.copy(

                        userHabit = userHabit,

                        habit = habit,

                        isLoading = false,

                        canCompleteToday =
                            userHabit != null &&
                                    !userHabit.completedToday &&
                                    !userHabit.isCompleted &&
                                    isTodaySelected(userHabit.selectedDays),

                        todaySelected =
                            userHabit?.let {
                                isTodaySelected(it.selectedDays)
                            } ?: false

                    )

                }

        }

    }

    fun completeToday() {

        val current = uiState.userHabit ?: return

        viewModelScope.launch {

            val result = completeHabitUseCase(current)

            result?.let { updatedHabit ->

                repository.updateHabit(updatedHabit)

                uiState = uiState.copy(

                    userHabit = updatedHabit,

                    completedToday = true,

                    showCompleteDialog = updatedHabit.isCompleted

                )

            }

        }

    }

    fun showCompletedDialog() {

        uiState = uiState.copy(

            showCompleteDialog = true

        )

    }

    fun dismissCompleteDialog() {

        uiState = uiState.copy(

            showCompleteDialog = false

        )

    }

    fun resetTodayState() {

        uiState = uiState.copy(

            completedToday = false

        )

    }

    // -------------------------
    // Delete Habit
    // -------------------------

    fun showDeleteDialog() {

        uiState = uiState.copy(

            showDeleteDialog = true

        )

    }

    fun dismissDeleteDialog() {

        uiState = uiState.copy(

            showDeleteDialog = false

        )

    }

    fun deleteHabit(

        onDeleted: () -> Unit

    ) {

        val currentHabit =
            uiState.userHabit
                ?: return

        viewModelScope.launch {

            repository.deleteHabit(currentHabit)

            uiState = uiState.copy(

                showDeleteDialog = false

            )

            onDeleted()

        }

    }

    private fun isTodaySelected(
        selectedDays: List<WeekDay>
    ): Boolean {

        val calendar = Calendar.getInstance()

        val today = when (calendar.get(Calendar.DAY_OF_WEEK)) {

            Calendar.SATURDAY -> WeekDay.SAT
            Calendar.SUNDAY -> WeekDay.SUN
            Calendar.MONDAY -> WeekDay.MON
            Calendar.TUESDAY -> WeekDay.TUE
            Calendar.WEDNESDAY -> WeekDay.WED
            Calendar.THURSDAY -> WeekDay.THU
            else -> WeekDay.FRI

        }

        return today in selectedDays

    }

}