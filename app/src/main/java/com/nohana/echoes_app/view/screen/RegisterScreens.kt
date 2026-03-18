package com.nohana.echoes_app.view.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme

@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit
) {
    var name by rememberSaveable() { mutableStateOf("") }
    var email by rememberSaveable() { mutableStateOf("") }
    var password by rememberSaveable() { mutableStateOf("") }

    Column(

    ) {

        OutlinedTextField(
            label = {Text(text = "Name")},
            value = name,
            onValueChange = { name = it }
        )

        OutlinedTextField(
            label = {Text(text = "E-mail")},
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        OutlinedTextField(
            label = {Text(text = "Senha")},
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = { onRegister(name, email, password) }
        ) {
            Text("Registrar")
        }
    }
}

@Composable
fun ValidateCode(
    email: String,
    onValidate: (String, String) -> Unit
) {
    var code by rememberSaveable() { mutableStateOf("") }

    Column(

    ) {

        Text("E-mail enviado para \n$email")

        OutlinedTextField(
            label = {Text(text = "Code")},
            value = code,
            onValueChange = { code = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Button(
            onClick = { onValidate(email, code) }
        ) {
            Text("Registrar")
        }
    }
}