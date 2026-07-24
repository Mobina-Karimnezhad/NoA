package com.noa.app.ui.screens.profile

import androidx.annotation.DrawableRes
import com.noa.app.R

data class AvatarOption(

    val name: String,

    @DrawableRes
    val imageRes: Int

)


val avatarOptions = listOf(

    AvatarOption(
        name = "avatar_1",
        imageRes = R.drawable.avatar_1
    ),

    AvatarOption(
        name = "avatar_2",
        imageRes = R.drawable.avatar_2
    ),

    AvatarOption(
        name = "avatar_3",
        imageRes = R.drawable.avatar_3
    ),

    AvatarOption(
        name = "avatar_4",
        imageRes = R.drawable.avatar_4
    ),

    AvatarOption(
        name = "avatar_5",
        imageRes = R.drawable.avatar_5
    ),

    AvatarOption(
        name = "avatar_6",
        imageRes = R.drawable.avatar_6
    ),

    AvatarOption(
        name = "avatar_7",
        imageRes = R.drawable.avatar_7
    ),

    AvatarOption(
        name = "avatar_8",
        imageRes = R.drawable.avatar_8
    ),

    AvatarOption(
        name = "avatar_9",
        imageRes = R.drawable.avatar_9
    ),

    AvatarOption(
        name = "avatar_10",
        imageRes = R.drawable.avatar_10
    )

)