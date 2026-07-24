package com.noa.app.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


@HiltViewModel
class ProfileViewModel @Inject constructor(

    private val repository:
    UserPreferencesRepository

) : ViewModel() {


    var uiState by mutableStateOf(

        ProfileUiState()

    )
        private set


    init {

        observeProfile()

    }


    private fun observeProfile() {

        viewModelScope.launch {

            repository.userName

                .combine(

                    repository.userAvatar

                ) { userName, avatarName ->

                    userName to avatarName

                }

                .collect { (userName, avatarName) ->

                    uiState =

                        uiState.copy(

                            userName =
                                userName,

                            avatarName =
                                avatarName,

                            selectedAvatarName =
                                avatarName,

                            avatarMarkedForDeletion =
                                false,

                            isLoading =
                                false

                        )

                }

        }

    }


    // -----------------------------
    // User Name
    // -----------------------------

    fun updateUserName(

        value: String

    ) {

        uiState =

            uiState.copy(

                userName =
                    value

            )

    }


    // -----------------------------
    // Avatar
    // -----------------------------

    fun selectAvatar(

        avatarName: String

    ) {

        uiState =

            uiState.copy(

                selectedAvatarName =
                    avatarName,

                avatarMarkedForDeletion =
                    false

            )

    }


    fun deleteAvatar() {

        uiState =

            uiState.copy(

                selectedAvatarName =
                    null,

                avatarMarkedForDeletion =
                    true

            )

    }


    // -----------------------------
    // Save Profile
    // -----------------------------

    fun saveProfile(

        onSaved: () -> Unit

    ) {

        if (
            uiState.userName.isBlank()
        ) {

            return

        }


        viewModelScope.launch {

            uiState =

                uiState.copy(

                    isSaving =
                        true,

                    saveCompleted =
                        false

                )


            try {

                repository.saveUserName(

                    uiState.userName
                        .trim()

                )


                repository.saveUserAvatar(

                    if (
                        uiState.avatarMarkedForDeletion
                    ) {

                        null

                    } else {

                        uiState.selectedAvatarName

                    }

                )


                uiState =

                    uiState.copy(

                        avatarName =

                            if (
                                uiState.avatarMarkedForDeletion
                            ) {

                                null

                            } else {

                                uiState.selectedAvatarName

                            },

                        selectedAvatarName =

                            if (
                                uiState.avatarMarkedForDeletion
                            ) {

                                null

                            } else {

                                uiState.selectedAvatarName

                            },

                        avatarMarkedForDeletion =
                            false,

                        isSaving =
                            false,

                        saveCompleted =
                            true

                    )


                onSaved()


            } catch (e: Exception) {

                e.printStackTrace()


                uiState =

                    uiState.copy(

                        isSaving =
                            false

                    )

            }

        }

    }


    // -----------------------------
    // Cancel
    // -----------------------------

    fun cancelChanges() {

        uiState =

            uiState.copy(

                selectedAvatarName =
                    uiState.avatarName,

                avatarMarkedForDeletion =
                    false

            )

    }

}