package com.nohana.echoes_app.view.screen


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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit,
    nameError: String? = null,
    emailError: String? = null,
    passwordError: String? = null,
    errorMessage: String? = null
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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

            OutlinedTextField(
                label = { Text("Nome") },
                value = name,
                onValueChange = { name = it },
                isError = nameError != null,
                supportingText = if (nameError != null) {
                    { Text(nameError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(6.dp))

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

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                label = { Text("Senha") },
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError != null || errorMessage != null,
                supportingText = if (passwordError != null) {
                    { Text(passwordError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onRegister(name, email, password) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Registrar", fontSize = 5.em)
        }
    }
}

@Composable
fun ValidateCode(
    email: String,
    onValidate: (String, String) -> Unit,
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

            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Icone"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "E-mail enviado para\n$email",
                textAlign = TextAlign.Center
            )

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
            onClick = { onValidate(email, code) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Verificar", fontSize = 5.em)
        }
    }
}