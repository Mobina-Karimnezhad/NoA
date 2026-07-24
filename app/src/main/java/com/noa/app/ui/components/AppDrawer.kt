package com.noa.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.Switch

@Composable
fun AppDrawer(

    drawerState: DrawerState,

    scope: CoroutineScope,

    onProfileClick: () -> Unit,

    isDarkTheme: Boolean,

    onDarkThemeChange: (Boolean) -> Unit

) {

    ModalDrawerSheet(

        modifier = Modifier
            .width(290.dp),

        drawerContainerColor = androidx.compose.material3.MaterialTheme
            .colorScheme
            .surfaceContainerLow

    ) {

        Column(

            modifier = Modifier
                .fillMaxHeight()
                .padding(

                    horizontal = 16.dp

                )

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),

                contentAlignment =
                    Alignment.Center

            ) {

                Text(

                    text = "منوی نوآ"

                )

            }

            NavigationDrawerItem(

                label = {

                    Text(

                        text = "پروفایل من"

                    )

                },

                selected = false,

                icon = {

                    Icon(

                        imageVector =
                            Icons.Default.Person,

                        contentDescription = null

                    )

                },

                onClick = {

                    scope.launch {

                        drawerState.close()

                    }

                    onProfileClick()

                }

            )

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 12.dp,
                            end = 12.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Text(

                    text =
                        "حالت شب",

                    modifier =
                        Modifier.weight(1f)

                )


                Switch(

                    checked =
                        isDarkTheme,

                    onCheckedChange = {

                        onDarkThemeChange(it)

                    }

                )

            }

        }

    }

}