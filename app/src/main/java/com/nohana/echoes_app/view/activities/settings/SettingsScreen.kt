package com.nohana.echoes_app.view.activities.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.components.ConfirmationDialog
import com.nohana.echoes_app.view.components.TextDialog
import com.nohana.echoes_app.view.components.TitleComponent

/**
 * Tela de configurações do aplicativo.
 *
 * Ainda sem conteúdo. As opções serão definidas e implementadas
 * em momento posterior.
 */
@Composable
fun SettingsScreen(
    onProfile: () -> Unit,
    onPrivacy: () -> Unit,
    onSecurity: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: (code: String) -> Unit
) {

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    ConfirmationDialog(
        showDialog = showLogoutDialog,
        title = "Sair da Conta",
        message = "Deseja realmente sair da sua conta?",
        confirmText = "Sair",
        confirmColor = DarkBlue,
        onConfirm = {
            showLogoutDialog = false
            onLogout()
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )

    Scaffold(
        topBar = {
            TitleComponent("Configurações")
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            SettingsItem(
                title = "Perfil",
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                onClick = onProfile
            )

            SettingsItem(
                title = "Privacidade",
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Face,
                        contentDescription = null
                    )
                },
                onClick = onPrivacy
            )

            SettingsItem(
                title = "Segurança",
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null
                    )
                },
                onClick = onSecurity
            )

            SettingsItem(
                title = "Logout",
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                onClick = {
                    showLogoutDialog = true
                }
            )
        }
    }
}

@Preview()
@Composable
fun SettingsScreenPreview() {
    EchoesAppTheme(
        darkTheme = false
    ) {
        SettingsScreen(
            onLogout = { },
            onPrivacy = { },
            onProfile = { },
            onSecurity = { },
            onDeleteAccount = { }
        )
    }
}

