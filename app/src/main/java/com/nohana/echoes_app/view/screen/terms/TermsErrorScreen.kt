package com.nohana.echoes_app.view.screen.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela de erro exibida quando o carregamento dos termos falha.
 *
 * Oferece ao usuário a opção de tentar novamente ou voltar à tela anterior.
 *
 * @param message  Mensagem descritiva do erro.
 * @param onRetry  Callback para tentar carregar os termos novamente.
 * @param onBack   Callback para voltar à tela anterior.
 */
@Composable
fun TermsErrorScreen(
    message: String,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Red,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Tentar Novamente", fontSize = 3.em)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(text = "Voltar", fontSize = 4.em)
        }
    }
}