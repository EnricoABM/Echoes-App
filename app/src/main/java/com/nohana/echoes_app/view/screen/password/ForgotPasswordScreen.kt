package com.nohana.echoes_app.view.screen.password

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.R


@Composable
fun ForgotPasswordEmailScreen(
    onSendCode: (email: String) -> Unit,
    emailError: String? = null,
    errorMessage: String? = null
) {
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.vet_icon), contentDescription = "Icone")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Informe seu e-mail para receber o código")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("E-mail") },
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError != null || errorMessage != null,
                supportingText = if (emailError != null) {
                    { Text(emailError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        Button(
            modifier = Modifier.width(250.dp),
            onClick = { onSendCode(email) },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue, contentColor = Color.White)
        ) { Text(text = "Enviar código", fontSize = 4.em) }
    }
}

@Composable
fun ForgotPasswordCodeScreen(
    email: String,
    onValidateCode: (code: String) -> Unit,
    codeError: String? = null,
    errorMessage: String? = null
) {
    var code by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.vet_icon), contentDescription = "Icone")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Caso o email esteja cadastrado.\nVocê receberá o código em breve")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Código") },
                value = code,
                onValueChange = { if (it.length <= 6) code = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("Digite o código de 6 dígitos") },
                isError = codeError != null || errorMessage != null,
                supportingText = if (codeError != null) {
                    { Text(codeError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onValidateCode(code) },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue, contentColor = Color.White)
        ) { Text(text = "Verificar código", fontSize = 4.em) }
    }
}

@Composable
fun ForgotPasswordNewPasswordScreen(
    onResetPassword: (newPassword: String, confirmPassword: String) -> Unit,
    newPasswordError: String? = null,
    confirmPasswordError: String? = null,
    errorMessage: String? = null
) {
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.vet_icon), contentDescription = "Icone")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Digite sua nova senha")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Nova senha") },
                value = newPassword,
                onValueChange = { newPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = newPasswordError != null || errorMessage != null,
                supportingText = if (newPasswordError != null) {
                    { Text(newPasswordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                label = { Text("Confirmar senha") },
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError != null || errorMessage != null,
                supportingText = if (confirmPasswordError != null) {
                    { Text(confirmPasswordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onResetPassword(newPassword, confirmPassword) },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue, contentColor = Color.White)
        ) { Text(text = "Redefinir senha", fontSize = 4.em) }
    }
}

@Composable
fun ForgotPasswordNewPasswordScreen(
    onResetPassword: (newPassword: String, confirmPassword: String) -> Unit,
    errorMessage: String = ""
) {
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Icone"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Digite sua nova senha")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Nova senha") },
                value = newPassword,
                onValueChange = { newPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = errorMessage.isNotBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                label = { Text("Confirmar senha") },
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = errorMessage.isNotBlank()
            )

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = Color.Red)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onResetPassword(newPassword, confirmPassword) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Redefinir senha", fontSize = 4.em)
        }
    }
}