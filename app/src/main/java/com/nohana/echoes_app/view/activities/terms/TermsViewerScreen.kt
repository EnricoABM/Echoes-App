package com.nohana.echoes_app.view.activities.terms

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela de visualização completa de um único termo em WebView.
 *
 * Exibe o conteúdo HTML recebido via Intent. Nenhuma chamada de rede
 * é realizada nesta tela — os dados já foram carregados pela [TermsActivity].
 *
 * @param content  Conteúdo HTML do termo a ser renderizado.
 * @param onClose  Callback invocado ao fechar a tela.
 */
@Composable
fun TermsViewerScreen(
    content: String,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // WebView ocupa toda a área disponível
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.javaScriptEnabled = false
                    isVerticalScrollBarEnabled = true
                    loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
                }
            },
            update = { webView ->
                webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
            }
        )

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