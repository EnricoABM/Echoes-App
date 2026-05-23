package com.nohana.echoes_app.view.activities.server

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.ui.theme.DarkBlue

@Composable
fun ServerConfigScreen(
    currentAddress: String = "",
    onSave: (address: String) -> Unit,
    errorMessage: String = ""
) {
    var ip by rememberSaveable { mutableStateOf(currentAddress) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Endereço do servidor")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("IP do servidor") },
                value = ip,
                onValueChange = { ip = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                isError = errorMessage.isNotBlank(),
                singleLine = true
            )

            if (errorMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = Color.Red)
            }
        }

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { onSave(ip) },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Salvar", fontSize = 4.em)
        }
    }
}