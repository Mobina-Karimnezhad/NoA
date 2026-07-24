package com.noa.app.ui.screens.profile

data class ProfileUiState(

    val userName: String = "",

    val avatarName: String? = null,

    val selectedAvatarName: String? = null,

    val avatarMarkedForDeletion: Boolean = false,

    val isLoading: Boolean = true,

    val isSaving: Boolean = false,

    val saveCompleted: Boolean = false

)