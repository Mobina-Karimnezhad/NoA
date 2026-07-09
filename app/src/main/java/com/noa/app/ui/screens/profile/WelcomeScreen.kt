package com.noa.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noa.app.R
import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.ui.theme.NoATheme
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(

    onContinue: () -> Unit

) {

    var name by remember {

        mutableStateOf("")

    }

    val context = LocalContext.current

    val repository = remember {

        UserPreferencesRepository(context)

    }

    val scope = rememberCoroutineScope()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Image(

            painter = painterResource(R.drawable.illustration_first_habit),

            contentDescription = null,

            modifier = Modifier.size(220.dp)

        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(

            text = "خوش اومدی 🌱",

            style = MaterialTheme.typography.headlineMedium,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "دوست داری با چه اسمی صدات کنیم؟",

            style = MaterialTheme.typography.bodyLarge,

            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(

            value = name,

            onValueChange = {

                name = it

            },

            modifier = Modifier.fillMaxWidth(),

            singleLine = true,

            keyboardOptions = KeyboardOptions(

                capitalization = KeyboardCapitalization.Words

            )

        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(

            enabled = name.isNotBlank(),

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            onClick = {

                scope.launch {

                    repository.saveUserName(name.trim())

                    onContinue()

                }

            }

        ) {

            Text("ادامه")

        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(

            onClick = {

                scope.launch {

                    repository.saveUserName("دوست من")

                    onContinue()

                }

            }

        ) {

            Text("فعلاً بدون نام ادامه می‌دهم")

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview() {

    NoATheme {

        WelcomeScreen(

            onContinue = {}

        )

    }

}