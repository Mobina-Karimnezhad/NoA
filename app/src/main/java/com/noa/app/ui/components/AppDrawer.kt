package com.noa.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(

    drawerState: DrawerState,

    scope: CoroutineScope,

    onProfileClick: () -> Unit,

    onAchievementsClick: () -> Unit,

    onMyPerformanceClick: () -> Unit,

    isDarkTheme: Boolean,

    onDarkThemeChange: (Boolean) -> Unit

) {

    ModalDrawerSheet(

        modifier =
            Modifier.width(290.dp),

        drawerContainerColor =
            MaterialTheme
                .colorScheme
                .surfaceContainerLow

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(
                        horizontal = 16.dp
                    )

        ) {

            // =====================================================
            // Drawer Header
            // =====================================================

            Box(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),

                contentAlignment =
                    Alignment.Center

            ) {

                Text(

                    text =
                        "منوی نوآ",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurface

                )

            }


            // =====================================================
            // Profile
            // =====================================================

            NavigationDrawerItem(

                label = {

                    Text(

                        text =
                            "پروفایل من"

                    )

                },

                selected =
                    false,

                icon = {

                    Icon(

                        imageVector =
                            Icons.Default.Person,

                        contentDescription =
                            "پروفایل من"

                    )

                },

                onClick = {

                    scope.launch {

                        drawerState.close()

                    }

                    onProfileClick()

                }

            )


            Spacer(

                modifier =
                    Modifier.height(8.dp)

            )


            // =====================================================
            // Achievements
            // =====================================================

            NavigationDrawerItem(

                label = {

                    Text(

                        text =
                            "دستاوردهای من"

                    )

                },

                selected =
                    false,

                icon = {

                    Icon(

                        imageVector =
                            Icons.Default.EmojiEvents,

                        contentDescription =
                            "دستاوردهای من"

                    )

                },

                onClick = {

                    scope.launch {

                        drawerState.close()

                    }

                    onAchievementsClick()

                }

            )


            Spacer(

                modifier =
                    Modifier.height(8.dp)

            )


            // =====================================================
            // My Performance
            // =====================================================

            NavigationDrawerItem(

                label = {

                    Text(

                        text =
                            "عملکرد من"

                    )

                },

                selected =
                    false,

                icon = {

                    Icon(

                        imageVector =
                            Icons.Default.Insights,

                        contentDescription =
                            "عملکرد من"

                    )

                },

                onClick = {

                    scope.launch {

                        drawerState.close()

                    }

                    onMyPerformanceClick()

                }

            )


            Spacer(

                modifier =
                    Modifier.height(16.dp)

            )


            // =====================================================
            // Dark Mode
            // =====================================================

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Text(

                    text =
                        "حالت شب",

                    modifier =
                        Modifier.weight(1f),

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurface

                )


                Switch(

                    checked =
                        isDarkTheme,

                    onCheckedChange =
                        onDarkThemeChange

                )

            }

        }

    }

}