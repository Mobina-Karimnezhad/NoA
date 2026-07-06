package com.noa.app.domain.model

import androidx.annotation.DrawableRes

data class Habit(

    val id: Int,

    val title: String,

    val description: String,

    @DrawableRes
    val imageRes: Int

)