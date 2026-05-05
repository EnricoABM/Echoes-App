package com.nohana.echoes_app.view.screen.terms

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.viewinterop.AndroidView
import com.nohana.echoes_app.network.dto.TermsResponseDTO
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela unificada de leitura dos termos do aplicativo.
 *
 * Exibe os Termos de Uso e a Política de Privacidade em sequência numa
 * única página com scroll, separados por um divisor. Nenhuma chamada à
 * API é realizada — a aceitação é registrada automaticamente pelo
 * servidor ao concluir o cadastro.
 *
 * @param termsOfUse    Dados dos Termos de Uso retornados pela API.
 * @param privacyPolicy Dados da Política de Privacidade retornados pela API.
 * @param onBack        Callback invocado quando o usuário clica em "Voltar".
 */
@Composable
fun TermsScreen(
    termsOfUse: TermsResponseDTO,
    privacyPolicy: TermsResponseDTO,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ── Termos de Uso ─────────────────────────────────────────────────────
        TermsSection(terms = termsOfUse)

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        Spacer(modifier = Modifier.height(24.dp))

        // ── Política de Privacidade ───────────────────────────────────────────
        TermsSection(terms = privacyPolicy)

        Spacer(modifier = Modifier.height(32.dp))

        // ── Botão voltar ──────────────────────────────────────────────────────
        Button(
            modifier = Modifier.width(200.dp),
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Voltar", fontSize = 4.em)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Seção individual de um documento de termos.
 *
 * Renderiza o título com a versão e o conteúdo HTML do termo
 * via [WebView] com altura fixa e scroll interno desabilitado,
 * delegando o scroll à [Column] pai.
 *
 * @param terms Dados do termo a ser exibido.
 */
@Composable
private fun TermsSection(terms: TermsResponseDTO) {
    val title = when (terms.type) {
        "TERMS_OF_USE"   -> "Termos de Uso"
        "PRIVACY_POLICY" -> "Política de Privacidade"
        else             -> terms.type.replace("_", " ")
    }

    Text(
        text = "$title — v${terms.version}",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // WebView com altura fixa; o scroll externo (Column) navega entre os termos.
    // JavaScript desabilitado por segurança — conteúdo é HTML estático.
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = false
                isVerticalScrollBarEnabled = true
                loadDataWithBaseURL(null, terms.content, "text/html", "UTF-8", null)
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(null, terms.content, "text/html", "UTF-8", null)
        }
    )
}