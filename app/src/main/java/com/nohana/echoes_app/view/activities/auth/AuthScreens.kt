package com.nohana.echoes_app.view.activities.auth

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.activities.password.PasswordResetActivity
import com.nohana.echoes_app.view.components.TextWithLink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: (email: String, password: String) -> Unit,
    error: String = "",
    emailError: String? = null,
    passwordError: String? = null
) {
    val context = LocalContext.current

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
                onValueChange = onEmailChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = error.isNotBlank() || emailError != null,
                supportingText = if (emailError != null) {
                    { Text(emailError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = DarkBlue
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                label = { Text("Senha") },
                value = password,
                onValueChange = onPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = error.isNotBlank() || passwordError != null,
                supportingText = if (passwordError != null) {
                    { Text(passwordError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = DarkBlue
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextWithLink(
                    prefixText = "Esqueceu a senha? ",
                    linkText = "Redifinir.",
                    onClick = {
                        context.startActivity(
                            Intent(context, PasswordResetActivity::class.java)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(1.dp))

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
                fontSize = 4.em
            )
        }
    }
}

@Composable
fun TwoFactorScreen(
    email: String,
    code: String,
    onCodeChange: (String) -> Unit,
    onTwoFactor: (String, String) -> Unit,
    error: String = ""
) {
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
                text = "E-mail enviado para \n$email",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                label = { Text("Código") },
                value = code,
                onValueChange = onCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text("Digite o código de 6 dígitos") },
                isError = error.isNotBlank(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = DarkBlue
                )
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
                fontSize = 4.em
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAuthScreen() {
    EchoesAppTheme {
        LoginScreen(
            email = "teste@email.com",
            password = "123456",
            onEmailChange = {},
            onPasswordChange = {},
            onLogin = { _, _ -> }
        )
    }
}