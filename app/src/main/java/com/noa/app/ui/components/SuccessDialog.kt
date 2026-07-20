package com.noa.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.noa.app.R

@Composable
fun SuccessDialog(

    onDismiss: () -> Unit

) {

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {

            Button(

                onClick = onDismiss

            ) {

                Text("باشه")

            }

        },

        shape = RoundedCornerShape(24.dp),

        title = null,

        text = {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center

            ) {

                Image(

                    painter = painterResource(R.drawable.ic_success),

                    contentDescription = null,

                    modifier = Modifier.size(96.dp)

                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(

                    text = "تبریک 🎉",

                    style = MaterialTheme.typography.headlineSmall

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = "شما امروز این عادت را با موفقیت انجام دادید.",

                    style = MaterialTheme.typography.bodyLarge,

                    modifier = Modifier.padding(horizontal = 8.dp)

                )

            }

        }

    )

}