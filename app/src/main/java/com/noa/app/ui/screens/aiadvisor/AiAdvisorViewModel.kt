package com.noa.app.ui.screens.aiadvisor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.core.ai.PendingAiSuggestionHolder
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.AiHabitSuggestion
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.usecase.GenerateHabitSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiAdvisorUiState(

    val focusArea: String? = null,

    val occupation: String? = null,

    val ageGroup: String? = null,

    val busyDays: Set<WeekDay> = emptySet(),

    val extraDetails: String = "",

    val isLoading: Boolean = false,

    val suggestion: AiHabitSuggestion? = null,

    val errorMessage: String? = null

)

@HiltViewModel
class AiAdvisorViewModel @Inject constructor(

    private val generateHabitSuggestionUseCase: GenerateHabitSuggestionUseCase,

    private val habitDataSource: DefaultHabitDataSource,

    private val pendingAiSuggestionHolder: PendingAiSuggestionHolder

) : ViewModel() {

    val habits = habitDataSource.getAll()

    var uiState by mutableStateOf(AiAdvisorUiState())
        private set

    fun selectFocusArea(value: String) {

        uiState = uiState.copy(focusArea = value)

    }

    fun selectOccupation(value: String) {

        uiState = uiState.copy(occupation = value)

    }

    fun selectAgeGroup(value: String) {

        uiState = uiState.copy(ageGroup = value)

    }

    fun toggleBusyDay(day: WeekDay) {

        val days =
            if (day in uiState.busyDays)
                uiState.busyDays - day
            else
                uiState.busyDays + day

        uiState = uiState.copy(busyDays = days)

    }

    fun updateExtraDetails(value: String) {

        if (value.length <= MAX_EXTRA_DETAILS_LENGTH) {

            uiState = uiState.copy(extraDetails = value)

        }

    }

    fun generateSuggestion() {

        val focusArea = uiState.focusArea
        val occupation = uiState.occupation
        val ageGroup = uiState.ageGroup

        if (focusArea == null ||
            occupation == null ||
            ageGroup == null
        ) {

            uiState = uiState.copy(
                errorMessage = "لطفاً همه‌ی گزینه‌ها رو انتخاب کن."
            )

            return

        }

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null,
            suggestion = null
        )

        viewModelScope.launch {

            val result =
                generateHabitSuggestionUseCase(

                    habits = habits,

                    focusArea = focusArea,

                    occupation = occupation,

                    ageGroup = ageGroup,

                    busyDays = uiState.busyDays.toList(),

                    extraDetails = uiState.extraDetails

                )

            result.fold(

                onSuccess = { suggestion ->

                    uiState = uiState.copy(
                        isLoading = false,
                        suggestion = suggestion,
                        errorMessage = null
                    )

                },

                onFailure = { error ->

                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message
                            ?: "خطایی پیش اومد. دوباره امتحان کن."
                    )

                }

            )

        }

    }

    fun acceptSuggestion(): Int? {

        val suggestion =
            uiState.suggestion ?: return null

        pendingAiSuggestionHolder.suggestion = suggestion

        return suggestion.habitId

    }

    companion object {

        const val MAX_EXTRA_DETAILS_LENGTH = 400

    }

}