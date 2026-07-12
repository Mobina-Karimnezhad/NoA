package com.noa.app.ui.screens.addhabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.repository.UserHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(

    private val repository: UserHabitRepository

) : ViewModel() {

    fun saveHabit(

        habit: UserHabit,

        onFinished: () -> Unit

    ) {

        viewModelScope.launch {

            repository.insertHabit(habit)

            onFinished()

        }

    }

}