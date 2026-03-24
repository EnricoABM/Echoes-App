package com.nohana.echoes_app.view.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.PasswordResetActivity
import com.nohana.echoes_app.view.state.LoginState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (email: String, password: String) -> Unit,
    error: String = "",
    emailError: String? = null,
    passwordError: String? = null
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Icone"
            )

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                label = { Text("E-mail") },
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = error.isNotBlank() || emailError != null,
                supportingText = if (emailError != null) {
                    { Text(emailError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                label = { Text("Senha") },
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = error.isNotBlank() || passwordError != null,
                supportingText = if (passwordError != null) {
                    { Text(passwordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            TextButton(
                onClick = {
                    context.startActivity(
                        Intent(context, PasswordResetActivity::class.java)
                    )
                }
            ) {
                Text("Esqueceu a Senha?")
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (error.isNotBlank()) {
                Text(text = error, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(52.dp))
        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onLogin(email, password) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Entrar",
                fontSize = 6.em
            )
        }
    }
}

@Composable
fun TwoFactorScreen(
    email: String,
    onTwoFactor: (String, String) -> Unit,
    error: String = ""
) {
    var code by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Icone",
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "E-mail enviado para $email",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                label = { Text("Código") },
                value = code,
                onValueChange = { code = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text("Digite o código de 6 dígitos") },
                isError = error.isNotBlank()
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (error.isNotBlank()) {
                Text(text = "Código Inválido", color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.height(52.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onTwoFactor(email, code) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Entrar",
                fontSize = 6.em
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAuthScreen() {
    EchoesAppTheme() {
        LoginScreen(
            {_, _-> },
            ""
        )
    }
}