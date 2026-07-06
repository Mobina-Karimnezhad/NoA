package com.noa.app.ui.screens.createhabit

sealed interface CreateHabitEvent {

    data object SaveHabit : CreateHabitEvent

}