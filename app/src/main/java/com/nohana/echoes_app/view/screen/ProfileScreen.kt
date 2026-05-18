package com.nohana.echoes_app.view.screen

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
import com.nohana.echoes_app.view.ChangePasswordActivity

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
    user: User,
    onLogout: () -> Unit
) {
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Nome", style = MaterialTheme.typography.labelMedium)
            TextField(
                onValueChange = {},
                readOnly = true,
                value = user.name
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text("E-mail", style = MaterialTheme.typography.labelMedium)
            TextField(
                onValueChange = {},
                readOnly = true,
                value = user.email
            )
        }

        // ── Ações ─────────────────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.width(220.dp),
                onClick = {
                    context.startActivity(Intent(context, ChangePasswordActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text("Alterar a Senha")
            }

            Button(
                modifier = Modifier.width(220.dp),
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Sair")
            }
        }
    }
}