package com.nohana.echoes_app.view.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.Composable
import com.nohana.echoes_app.network.dto.TermsResponseDTO
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela de leitura de um termo de uso ou política de privacidade.
 *
 * Exibe o conteúdo HTML do termo em um [WebView] em modo somente leitura.
 * Nenhuma chamada à API é realizada a partir desta tela — a aceitação
 * é registrada automaticamente após o registro bem-sucedido do usuário.
 *
 * @param terms    Dados do termo a ser exibido.
 * @param onClose  Callback invocado quando o usuário fecha a tela.
 */
@Composable
fun TermsScreen(
    terms: TermsResponseDTO,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${terms.type.replace("_", " ")} — v${terms.version}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Renderiza o HTML do termo. JavaScript desabilitado por segurança.
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.LightGray, shape = MaterialTheme.shapes.small),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = false
                        loadDataWithBaseURL(null, terms.content, "text/html", "UTF-8", null)
                    }
                },
                update = { webView ->
                    webView.loadDataWithBaseURL(null, terms.content, "text/html", "UTF-8", null)
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Fechar", fontSize = 4.em)
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}