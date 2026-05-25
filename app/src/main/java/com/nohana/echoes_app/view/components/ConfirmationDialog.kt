package com.nohana.echoes_app.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nohana.echoes_app.ui.theme.DarkBlue

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    confirmText: String = "Confirmar",
    dismissText: String = "Cancelar",
    confirmColor: Color = MaterialTheme.colorScheme.primary,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(text = title)
        },

        text = {
            Text(
                text = message
            )
        },

        confirmButton = {

            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = confirmText,
                    color = confirmColor
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissText,
                    color = DarkBlue
                )
            }
        }
    )
}