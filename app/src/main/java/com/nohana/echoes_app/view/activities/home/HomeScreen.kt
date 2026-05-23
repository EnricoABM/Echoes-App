package com.nohana.echoes_app.view.activities.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela principal do aplicativo — lista de dispositivos vinculados.
 *
 * Ainda sem conteúdo. Exibe apenas um estado vazio enquanto a
 * funcionalidade de dispositivos não for implementada.
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.vet_icon),
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = DarkBlue.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Text(
            text = "Nenhum dispositivo vinculado",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}