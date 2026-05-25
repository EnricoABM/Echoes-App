package com.nohana.echoes_app.view.activities.reactive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.states.ReactiveState

@Composable
fun ReactivityAccountScreen(
    onBackFunction: () -> Unit,
    onReactiveRequest: (email: String) -> Unit,
    onReactiveAccount: (email: String, code: String) -> Unit,
    state: ReactiveState
) {
    Scaffold(
        topBar = {
            TitleComponent(
                text = "Reativação",
                backFunction = onBackFunction
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {

            Icon(
                painter = painterResource(R.drawable.vet_icon),
                contentDescription = "Icone"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Email conectado ao Estado
            OutlinedTextField(
                value = state.email,
                onValueChange = { newEmail ->

                },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Código conectado ao Estado
            OutlinedTextField(
                value = state.code,
                onValueChange = { newCode ->

                },
                label = { Text("Código de Reativação") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // O botão reage ao estado de Loading
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar Reativação")
                }
            }
            


        }

    }
}