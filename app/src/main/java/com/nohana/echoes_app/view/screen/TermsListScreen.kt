package com.nohana.echoes_app.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.network.dto.TermsResponseDTO
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Tela que lista os termos disponíveis no sistema.
 *
 * Cada termo é exibido como um card clicável. Ao clicar, o usuário
 * é direcionado para a [TermsViewerActivity] que exibe o conteúdo
 * completo em uma WebView.
 *
 * @param termsOfUse    Dados dos Termos de Uso.
 * @param privacyPolicy Dados da Política de Privacidade.
 * @param onOpenTerms   Callback com o termo selecionado pelo usuário.
 * @param onBack        Callback para voltar à tela de registro.
 */
@Composable
fun TermsListScreen(
    termsOfUse: TermsResponseDTO,
    privacyPolicy: TermsResponseDTO,
    onOpenTerms: (terms: TermsResponseDTO) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Documentos disponíveis",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Card: Termos de Uso ───────────────────────────────────────────
            TermsItemCard(
                title = "Termos de Uso",
                version = termsOfUse.version,
                iconRes = R.drawable.vet_icon,
                onClick = { onOpenTerms(termsOfUse) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Card: Política de Privacidade ─────────────────────────────────
            TermsItemCard(
                title = "Política de Privacidade",
                version = privacyPolicy.version,
                iconRes = R.drawable.vet_icon,
                onClick = { onOpenTerms(privacyPolicy) }
            )
        }

        // ── Botão Voltar ──────────────────────────────────────────────────────
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
    }
}

/**
 * Card clicável que representa um item de termo na lista.
 *
 * @param title   Nome do documento exibido ao usuário.
 * @param version Versão atual do documento.
 * @param iconRes Recurso do ícone decorativo.
 * @param onClick Callback invocado ao clicar no card.
 */
@Composable
private fun TermsItemCard(
    title: String,
    version: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = DarkBlue
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Versão $version",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Abrir",
                modifier = Modifier.size(16.dp),
                tint = Color.Gray
            )
        }
    }
}