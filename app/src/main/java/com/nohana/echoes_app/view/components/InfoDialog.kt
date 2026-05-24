package com.nohana.echoes_app.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InfoDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    buttonText: String = "OK",
    onDismiss: () -> Unit
) {
    if (!showDialog) return
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(buttonText)
            }
        }
    )
}