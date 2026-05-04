package com.nohana.echoes_app.view.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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


/**
 * Tela de cadastro de novo usuário.
 *
 * Exibe os campos de nome, e-mail, senha e confirmação de senha, além de um
 * checkbox de aceite obrigatório com links para leitura dos Termos de Uso e
 * da Política de Privacidade. O ícone do app é exibido no topo, seguindo o
 * padrão visual das demais telas do aplicativo.
 *
 * @param onRegister           Chamado com os dados do formulário ao submeter.
 * @param nameError            Mensagem de erro para o campo nome.
 * @param emailError           Mensagem de erro para o campo e-mail.
 * @param passwordError        Mensagem de erro para o campo senha.
 * @param confirmPasswordError Mensagem de erro para a confirmação de senha.
 * @param termsError           Mensagem de erro quando o checkbox não foi marcado.
 * @param errorMessage         Mensagem de erro geral retornada pela API.
 */
@Composable
fun RegisterScreen(
    onRegister: (name: String, email: String, password: String, confirmPassword: String, termsAccepted: Boolean) -> Unit,    onViewTerms: () -> Unit,   // ← único callback de termos
    nameError: String? = null,
    emailError: String? = null,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
    termsError: String? = null,
    errorMessage: String? = null
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var termsAccepted by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ── Ícone do app (padrão de todas as telas) ───────────────────────────
        Icon(
            painter = painterResource(R.drawable.vet_icon),
            contentDescription = "Icone"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Campos do formulário ──────────────────────────────────────────────
        OutlinedTextField(
            label = { Text("Nome") },
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError != null || errorMessage != null,
            supportingText = if (passwordError != null) {
                { Text(passwordError, color = MaterialTheme.colorScheme.error) }
            } else null
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            label = { Text("Confirme sua Senha") },
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = confirmPasswordError != null || errorMessage != null,
            supportingText = if (confirmPasswordError != null) {
                { Text(confirmPasswordError, color = MaterialTheme.colorScheme.error) }
            } else null
        )

        // ── Erro geral da API ─────────────────────────────────────────────────
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Checkbox de aceite dos termos ─────────────────────────────────────
        // ── Checkbox de aceite dos termos ─────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it }
            )
            Text(text = "Li e aceito os ")
            TextButton(
                onClick = onViewTerms,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Termos e Políticas",
                    softWrap = true
                )
            }
        }

        if (termsError != null) {
            Text(
                text = termsError,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Botão de cadastro ─────────────────────────────────────────────────
        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onRegister(name, email, password, confirmPassword, termsAccepted) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Registrar", fontSize = 5.em)
        }

        Spacer(modifier = Modifier.height(16.dp))
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