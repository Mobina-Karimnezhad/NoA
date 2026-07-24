package com.noa.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.R
import com.noa.app.ui.screens.profile.avatarOptions
import com.noa.app.ui.theme.NoATheme
import com.noa.app.ui.theme.TextSecondary
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeHeader(

    userName: String? = null,

    userAvatarName: String? = null,

    onMenuClick: () -> Unit = {}

) {

    val greeting =

        if (userName.isNullOrBlank())
            "سلام دوست من 🌱"
        else
            "سلام $userName 🌱"


    val selectedAvatar =

        avatarOptions.find {

            it.name == userAvatarName

        }


    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(

                color =
                    MaterialTheme
                        .colorScheme
                        .primaryContainer

            )
            .padding(

                horizontal = 10.dp,

                vertical = 16.dp

            ),

        verticalAlignment =
            Alignment.CenterVertically,

        horizontalArrangement =
            Arrangement.SpaceBetween

    ) {


        Row(

            verticalAlignment =
                Alignment.CenterVertically

        ) {


            IconButton(

                onClick =
                    onMenuClick

            ) {

                Icon(

                    imageVector =
                        Icons.Default.Menu,

                    contentDescription =
                        "منوی برنامه",

                    modifier =
                        Modifier.size(25.dp),

                    tint =
                        MaterialTheme
                            .colorScheme
                            .onPrimaryContainer

                )

            }


            Spacer(

                modifier =
                    Modifier.width(
                        10.dp
                    )

            )


            Column(

                horizontalAlignment =
                    Alignment.Start

            ) {

                Text(

                    text =
                        greeting,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Light,

                    textAlign =
                        TextAlign.Start

                )


                Spacer(

                    modifier =
                        Modifier.height(
                            10.dp
                        )

                )


                Text(

                    text =
                        "امروز هم یک قدم بهتر از دیروز باش.",

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onPrimaryContainer,

                    textAlign =
                        TextAlign.Start

                )

            }


            Spacer(

                modifier =
                    Modifier.width(
                        15.dp
                    )

            )

        }


        Image(

            painter = painterResource(

                id =
                    selectedAvatar?.imageRes
                        ?: R.drawable.default_avatar

            ),

            contentDescription =

                if (
                    selectedAvatar == null
                )
                    "آواتار پیش‌فرض"
                else
                    "آواتار کاربر",

            modifier =
                Modifier
                    .size(100.dp)
                    .clip(
                        RoundedCornerShape(
                            20.dp
                        )
                    )

        )

    }

}


@Preview(

    showBackground = true,

    showSystemUi = true

)
@Composable
fun HomeHeaderPreview() {

    NoATheme {

        HomeHeader(

            userName =
                "مبینا",

            userAvatarName =
                "avatar_3",

            onMenuClick =
                {}

        )

    }

}