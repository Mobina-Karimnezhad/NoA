package com.noa.app.ui.screens.edithabit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.repository.UserHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(

    private val repository: UserHabitRepository,

    private val habitDataSource: DefaultHabitDataSource,

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val habitId: Int =
        savedStateHandle
            .get<String>("habitId")
            ?.toInt()
            ?: error("Habit id not found")

    var uiState by mutableStateOf(
        EditHabitUiState()
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

                    if (userHabit == null) {
                        return@collectLatest
                    }

                    val habit =
                        habitDataSource
                            .getAll()
                            .firstOrNull {
                                it.id == userHabit.habitId
                            }

                    uiState = uiState.copy(

                        userHabit = userHabit,

                        habit = habit,

                        customTitle =
                            userHabit.customTitle,

                        targetDays =
                            userHabit.targetDays,

                        selectedDays =
                            userHabit.selectedDays,

                        reminderTime =
                            userHabit.reminderTime,

                        isLoading = false

                    )

                }

        }

    }

    fun updateTitle(value: String) {

        uiState = uiState.copy(

            customTitle = value

        )

    }

    fun increaseTargetDays() {

        uiState = uiState.copy(

            targetDays =
                uiState.targetDays + 1

        )

    }

    fun decreaseTargetDays() {

        if (uiState.targetDays > 1) {

            uiState = uiState.copy(

                targetDays =
                    uiState.targetDays - 1

            )

        }

    }

    fun toggleDay(day: WeekDay) {

        val days =

            if (day in uiState.selectedDays) {

                uiState.selectedDays - day

            } else {

                uiState.selectedDays + day

            }

        uiState = uiState.copy(

            selectedDays = days

        )

    }

    fun updateReminderTime(time: String) {

        uiState = uiState.copy(

            reminderTime = time

        )

    }

    fun saveHabit(

        onFinished: () -> Unit

    ) {

        val currentHabit =
            uiState.userHabit
                ?: return

        if (
            uiState.customTitle.isBlank() ||
            uiState.targetDays < 21 ||
            uiState.selectedDays.isEmpty()
        ) {
            return
        }

        val updatedHabit =

            currentHabit.copy(

                customTitle =
                    uiState.customTitle.trim(),

                targetDays =
                    uiState.targetDays,

                selectedDays =
                    uiState.selectedDays,

                reminderTime =
                    uiState.reminderTime

            )

        viewModelScope.launch {

            uiState = uiState.copy(

                isSaving = true

            )

            repository.updateHabit(

                updatedHabit

            )

            uiState = uiState.copy(

                userHabit = updatedHabit,

                isSaving = false

            )

            onFinished()

        }

    }

}