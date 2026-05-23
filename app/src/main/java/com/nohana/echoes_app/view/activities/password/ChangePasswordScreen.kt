package com.nohana.echoes_app.view.activities.password

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
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue

@Composable
fun ChangePasswordCurrentScreen(
    onValidate: (currentPassword: String) -> Unit,
    errorMessage: String = "",
    passwordError: String? = null
) {
    var currentPassword by rememberSaveable { mutableStateOf("") }

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

            Text("Confirme sua senha atual")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Senha atual") },
                value = currentPassword,
                onValueChange = { currentPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = errorMessage.isNotBlank() || passwordError != null,
                supportingText = if (passwordError != null) {
                    { Text(text = passwordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = Color.Red)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onValidate(currentPassword) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Confirmar", fontSize = 4.em)
        }
    }
}

@Composable
fun ChangePasswordNewScreen(
    onChangePassword: (newPassword: String, confirmPassword: String) -> Unit,
    errorMessage: String = "",
    newPasswordError: String? = null,
    confirmPasswordError: String? = null
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
                isError = errorMessage.isNotBlank() || newPasswordError != null,
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
                isError = errorMessage.isNotBlank() || confirmPasswordError != null,
                supportingText = if (confirmPasswordError != null) {
                    { Text(confirmPasswordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = Color.Red)
            }
        }

        Button(
            modifier = Modifier.width(250.dp),
            onClick = { onChangePassword(newPassword, confirmPassword) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Alterar senha", fontSize = 4.em)
        }
    }
}