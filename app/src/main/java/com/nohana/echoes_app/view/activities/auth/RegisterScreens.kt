package com.nohana.echoes_app.view.activities.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue

@Composable
fun RegisterScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    termsAccepted: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    onRegister: (String, String, String, String, Boolean) -> Unit,
    onViewTerms: () -> Unit,
    nameError: String? = null,
    emailError: String? = null,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
    termsError: String? = null,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Ícone",
                modifier = Modifier.size(240.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nome") },
                isError = nameError != null,
                supportingText = if (nameError != null) {
                    { Text(nameError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError != null,
                supportingText = if (emailError != null) {
                    { Text(emailError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError != null,
                supportingText = if (passwordError != null) {
                    { Text(passwordError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Confirmar Senha") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError != null,
                supportingText = if (confirmPasswordError != null) {
                    { Text(confirmPasswordError, color = MaterialTheme.colorScheme.error) }
                } else null,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = onTermsAcceptedChange
                )

                Text(
                    text = "Aceito os termos de uso",
                    modifier = Modifier.clickable { onViewTerms() },
                    color = DarkBlue
                )
            }

            if (termsError != null) {
                Text(
                    text = termsError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (!errorMessage.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                onRegister(
                    name,
                    email,
                    password,
                    confirmPassword,
                    termsAccepted
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Registrar",
                fontSize = 4.em
            )
        }
    }
}

@Composable
fun ValidateCode(
    email: String,
    code: String,
    onCodeChange: (String) -> Unit,
    onValidate: (String) -> Unit,
    codeError: String? = null,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Ícone"
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Código enviado para\n$email",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                label = { Text("Código") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("Digite o código de 6 dígitos") },
                isError = codeError != null || !errorMessage.isNullOrBlank(),
                supportingText = when {
                    codeError != null -> {
                        { Text(codeError, color = MaterialTheme.colorScheme.error) }
                    }
                    !errorMessage.isNullOrBlank() -> {
                        { Text(errorMessage, color = MaterialTheme.colorScheme.error) }
                    }
                    else -> null
                },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onValidate(code) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Validar",
                fontSize = 4.em
            )
        }
    }
}