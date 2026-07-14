package com.noa.app.ui.screens.choosehabit

import androidx.lifecycle.ViewModel
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseFirstHabitViewModel @Inject constructor(

    private val repository: DefaultHabitDataSource

) : ViewModel() {

    val habits: List<Habit> =
        repository.getAll()

}