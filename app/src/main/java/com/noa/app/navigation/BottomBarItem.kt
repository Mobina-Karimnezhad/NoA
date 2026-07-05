package com.noa.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(

    val route: String,

    val title: String,

    val icon: ImageVector

) {

    data object Home : BottomBarItem(
        Routes.Home.route,
        "خانه",
        Icons.Default.Home
    )

    data object Statistics : BottomBarItem(
        Routes.Statistics.route,
        "آمار",
        Icons.Default.AutoGraph
    )

    data object AI : BottomBarItem(
        Routes.AI.route,
        "نوآ",
        Icons.Default.Psychology
    )

    data object Settings : BottomBarItem(
        Routes.Settings.route,
        "تنظیمات",
        Icons.Default.Settings
    )

}