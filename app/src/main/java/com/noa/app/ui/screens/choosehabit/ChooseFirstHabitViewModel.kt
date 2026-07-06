package com.noa.app.ui.screens.choosehabit

import androidx.lifecycle.ViewModel
import com.noa.app.data.repository.HabitRepository
import com.noa.app.domain.model.Habit

class ChooseFirstHabitViewModel : ViewModel() {

    private val repository = HabitRepository()

    val habits: List<Habit> =
        repository.getSuggestedHabits()

}