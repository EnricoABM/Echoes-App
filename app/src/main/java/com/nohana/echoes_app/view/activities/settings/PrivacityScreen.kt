package com.nohana.echoes_app.view.activities.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.nohana.echoes_app.view.components.TitleComponent

@Composable
fun PrivacityScreen(
    onOpenTerms: () -> Unit,
    onRevokeTerms: () -> Unit,
    onExportData: () -> Unit,
    onBackFunction: () -> Unit,
    snackbar: SnackbarHostState
) {

    var showRevokeDialog by remember {
        mutableStateOf(false)
    }

    ConfirmationDialog(
        showDialog = showRevokeDialog,
        title = "Revogar Termos",
        message = "Tem certeza que deseja revogar os termos aceitos?\n\nIsso poderá impedir o uso do aplicativo.",
        confirmText = "Revogar",
        confirmColor = Color.Red,
        onConfirm = {
            showRevokeDialog = false
            onRevokeTerms()
        },
        onDismiss = {
            showRevokeDialog = false
        }
    )

    Scaffold(
        topBar = {
            TitleComponent(
                text = "Privacidade",
                backFunction = onBackFunction
            )
        },
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            SettingsItem(
                title = "Ver Termos",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = DarkBlue
                    )
                },
                onClick = onOpenTerms
            )

            SettingsItem(
                title = "Exportar Dados",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = DarkBlue
                    )
                },
                onClick = onExportData
            )

            SettingsItem(
                title = "Revogar Termos",
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                },
                textColor = Color.Red,
                onClick = {
                    showRevokeDialog = true
                }
            )
        }
    }
}

@Preview
@Composable
fun PrivacyScreenPreview() {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    EchoesAppTheme {
        PrivacityScreen(
            onOpenTerms = { },
            onRevokeTerms = { },
            onExportData = { },
            onBackFunction = { },
            snackbar = snackbarHostState
        )
    }
}