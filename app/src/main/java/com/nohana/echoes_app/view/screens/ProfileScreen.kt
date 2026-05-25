package com.nohana.echoes_app.view.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.view.activities.password.ChangePasswordActivity

/**
 * Tela de perfil do usuário autenticado.
 *
 * Exibe nome e e-mail (somente leitura) e disponibiliza as ações
 * de alteração de senha e saída do aplicativo.
 *
 * @param user      Dados do usuário carregados pela API.
 * @param onLogout  Callback invocado ao pressionar "Sair".
 */
@Composable
fun ProfileScreen(
    user: User
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── Avatar ────────────────────────────────────────────────────────────
        Image(
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
                .background(Color.LightGray, CircleShape),
            painter = painterResource(R.drawable.person_image),
            contentDescription = "Imagem de Perfil"
        )

        // ── Dados do usuário ──────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            OutlinedTextField(
                label = {
                    Text("Nome", style = MaterialTheme.typography.labelMedium)
                },
                onValueChange = {},
                readOnly = true,
                value = user.name,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = DarkBlue
                )
            )


            Spacer(modifier = Modifier.height(1.dp))


            OutlinedTextField(
                label = {
                    Text("E-mail", style = MaterialTheme.typography.labelMedium)
                },
                onValueChange = {},
                readOnly = true,
                value = user.email,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = DarkBlue
                )
            )
        }
    }
}