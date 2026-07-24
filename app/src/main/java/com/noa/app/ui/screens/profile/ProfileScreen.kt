package com.noa.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noa.app.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.offset



@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun ProfileScreen(

    onBack: () -> Unit,

    onSaved: () -> Unit,

    viewModel: ProfileViewModel =
        hiltViewModel()

) {

    val uiState =
        viewModel.uiState



    var showAvatarDialog by remember {

        mutableStateOf(false)

    }




    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(

                        text =
                            "پروفایل من"

                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick =
                            onBack

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default
                                    .ArrowBack,

                            contentDescription =
                                "بازگشت"

                        )

                    }

                }

            )

        }

    ) { padding ->


        if (
            uiState.isLoading
        ) {

            Box(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            padding
                        ),

                contentAlignment =
                    Alignment.Center

            ) {

                CircularProgressIndicator()

            }

        } else {


            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            padding
                        )
                        .padding(
                            horizontal =
                                24.dp
                        )
                        .padding(
                            vertical =
                                24.dp
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Top

            ) {


                Box(

                    contentAlignment =
                        Alignment.BottomEnd

                ) {


                    val displayedAvatarName =

                        uiState.selectedAvatarName


                    val selectedAvatar =

                        avatarOptions.find {

                            it.name == displayedAvatarName

                        }

                    if (selectedAvatar == null) {

                        Image(

                            painter =
                                painterResource(

                                    id =
                                        R.drawable.default_avatar

                                ),

                            contentDescription =
                                "آواتار پیش‌فرض",

                            modifier =
                                Modifier
                                    .size(140.dp)
                                    .clip(
                                        CircleShape
                                    ),

                            contentScale =
                                ContentScale.Crop

                        )

                    } else {

                        Image(

                            painter =
                                painterResource(

                                    id =
                                        selectedAvatar.imageRes

                                ),

                            contentDescription =
                                "آواتار کاربر",

                            modifier =
                                Modifier
                                    .size(140.dp)
                                    .clip(
                                        CircleShape
                                    ),

                            contentScale =
                                ContentScale.Crop

                        )

                    }


                    Row(

                        horizontalArrangement =
                            Arrangement.spacedBy(
                                8.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        if (
                            !uiState.selectedAvatarName
                                .isNullOrBlank()
                        ) {


                            Box(

                                modifier =
                                    Modifier
                                        .size(
                                            42.dp
                                        )
                                        .offset(
                                            y = 8.dp
                                        )
                                        .clip(
                                            CircleShape
                                        )
                                        .background(

                                            MaterialTheme
                                                .colorScheme
                                                .error

                                        )
                                        .clickable {

                                            viewModel.deleteAvatar()

                                        },

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default
                                            .Delete,

                                    contentDescription =
                                        "حذف تصویر پروفایل",

                                    tint =
                                        MaterialTheme
                                            .colorScheme
                                            .onError

                                )

                            }

                        }


                        Box(

                            modifier =
                                Modifier
                                    .size(42.dp)
                                    .clip(
                                        CircleShape
                                    )
                                    .background(

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    )
                                    .clickable {

                                        showAvatarDialog =
                                            true

                                    },

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription =
                                    "تغییر آواتار",

                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .onPrimary

                            )

                        }

                    }

                }


                Spacer(

                    modifier =
                        Modifier
                            .height(
                                16.dp
                            )

                )


                Text(

                    text =
                        "برای تغییر تصویر پروفایل روی آواتار بزنید.",

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant,

                    textAlign =
                        TextAlign.Center

                )


                Spacer(

                    modifier =
                        Modifier
                            .height(
                                32.dp
                            )

                )


                OutlinedTextField(

                    value =
                        uiState.userName,

                    onValueChange = {

                        viewModel
                            .updateUserName(
                                it
                            )

                    },

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    label = {

                        Text(
                            "نام شما"
                        )

                    },

                    placeholder = {

                        Text(
                            "مثلاً مبینا"
                        )

                    },

                    singleLine =
                        true,

                    shape =
                        RoundedCornerShape(
                            16.dp
                        )

                )


                Spacer(

                    modifier =
                        Modifier
                            .height(
                                32.dp
                            )

                )


                Button(

                    onClick = {

                        viewModel.saveProfile {

                            onSaved()

                        }

                    },

                    enabled =
                        !uiState.isSaving &&
                                uiState.userName
                                    .isNotBlank(),

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        )

                ) {


                    if (
                        uiState.isSaving
                    ) {


                        CircularProgressIndicator(

                            modifier =
                                Modifier
                                    .size(
                                        22.dp
                                    ),

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onPrimary,

                            strokeWidth =
                                2.dp

                        )


                    } else {


                        Text(

                            text =
                                "ذخیره تغییرات",

                            fontWeight =
                                FontWeight.Medium

                        )

                    }

                }


                Spacer(

                    modifier =
                        Modifier
                            .height(
                                12.dp
                            )

                )


                OutlinedButton(

                    onClick = {

                        viewModel.cancelChanges()

                        onBack()

                    },

                    enabled =
                        !uiState.isSaving,

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        )

                ) {

                    Text(

                        text =
                            "انصراف"

                    )

                }


            }

        }

    }

    if (showAvatarDialog) {

        AvatarSelectionDialog(

            selectedAvatarName =
                uiState.selectedAvatarName,

            onAvatarSelected = {

                viewModel.selectAvatar(it)

            },

            onDismiss = {

                showAvatarDialog =
                    false

            }

        )

    }

}

@Composable
fun AvatarSelectionDialog(

    selectedAvatarName: String?,

    onAvatarSelected: (String) -> Unit,

    onDismiss: () -> Unit

) {

    androidx.compose.material3.AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(

                text =
                    "انتخاب تصویر پروفایل",

                textAlign =
                    TextAlign.Center,

                modifier =
                    Modifier.fillMaxWidth()

            )

        },

        text = {

            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(

                columns =
                    androidx.compose.foundation.lazy.grid
                        .GridCells
                        .Fixed(3),

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        12.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(
                        12.dp

                    )

            ) {

                items(
                    avatarOptions.size
                ) { index ->

                    val avatar =
                        avatarOptions[index]


                    Box(

                        modifier =
                            Modifier
                                .size(72.dp)
                                .clip(
                                    CircleShape
                                )
                                .clickable {

                                    onAvatarSelected(

                                        avatar.name

                                    )

                                    onDismiss()

                                },

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Image(

                            painter =
                                painterResource(

                                    id =
                                        avatar.imageRes

                                ),

                            contentDescription =
                                "آواتار ${index + 1}",

                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .clip(
                                        CircleShape
                                    ),

                            contentScale =
                                ContentScale.Crop

                        )


                        if (
                            selectedAvatarName ==
                            avatar.name
                        ) {

                            Box(

                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(

                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                                .copy(
                                                    alpha =
                                                        0.35f
                                                )

                                        )

                            )

                        }

                    }

                }

            }

        },

        confirmButton = {

            OutlinedButton(

                onClick =
                    onDismiss

            ) {

                Text(
                    "بستن"
                )

            }

        }

    )

}