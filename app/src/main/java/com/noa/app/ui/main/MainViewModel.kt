package com.noa.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.domain.usecase.RefreshDailyHabitsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val refreshDailyHabitsUseCase:
    RefreshDailyHabitsUseCase,

    private val userPreferencesRepository:
    UserPreferencesRepository

) : ViewModel() {


    // وضعیت حالت شب
    // مقدار پیش‌فرض false یعنی حالت روز
    val isDarkTheme =

        userPreferencesRepository
            .isDarkTheme
            .stateIn(

                scope =
                    viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(
                            5000
                        ),

                initialValue =
                    false

            )


    val userName =

        userPreferencesRepository
            .userName
            .stateIn(

                scope =
                    viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(
                            5000
                        ),

                initialValue =
                    "دوست من"

            )


    val userAvatarName =

        userPreferencesRepository
            .userAvatar
            .stateIn(

                scope =
                    viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(
                            5000
                        ),

                initialValue =
                    null

            )


    init {

        viewModelScope.launch {

            refreshDailyHabitsUseCase()

        }

    }


    // تغییر حالت شب / روز
    fun setDarkTheme(

        enabled: Boolean

    ) {

        viewModelScope.launch {

            userPreferencesRepository
                .setDarkTheme(
                    enabled
                )

        }

    }

}