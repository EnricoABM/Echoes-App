package com.nohana.echoes_app.view.activities.terms

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.network.dto.TermsResponseDTO
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.components.LoadingScreen
import com.nohana.echoes_app.viewmodel.TermsViewModel
import com.nohana.echoes_app.viewmodel.factory.TermsViewModelFactory
import kotlin.jvm.java

/**
 * Activity que exibe a lista de termos disponíveis no sistema.
 *
 * Ao clicar em um item, abre a [TermsViewerActivity] passando o conteúdo
 * HTML via Intent. O botão "Voltar" encerra esta Activity, retornando
 * automaticamente à [com.nohana.echoes_app.view.activities.auth.RegisterActivity].
 */
class TermsActivity : ComponentActivity() {

    private val viewModel: TermsViewModel by viewModels {
        TermsViewModelFactory(
            NetworkProvider.getAddress(),
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState()

            Column {
                TitleComponent("Termos e Políticas")

                when (val s = state) {

                    TermsActivityState.Loading -> LoadingScreen()

                    is TermsActivityState.Success -> TermsListScreen(
                        termsOfUse = s.termsOfUse,
                        privacyPolicy = s.privacyPolicy,
                        onOpenTerms = { terms -> openTermsViewer(terms) },
                        onBack = { finish() }
                    )

                    is TermsActivityState.Error -> {
                        // Exibe erro com opção de tentar novamente
                        TermsErrorScreen(
                            message = s.message,
                            onRetry = viewModel::loadTerms,
                            onBack = { finish() }
                        )
                    }
                }
            }
        }
    }

    /**
     * Abre a [TermsViewerActivity] passando o conteúdo e o título do
     * termo selecionado via extras do Intent.
     *
     * @param terms Termo selecionado pelo usuário na lista.
     */
    private fun openTermsViewer(terms: TermsResponseDTO) {
        val intent = Intent(this, TermsViewerActivity::class.java).apply {
            putExtra(TermsViewerActivity.EXTRA_TITLE, resolveTitle(terms.type))
            putExtra(TermsViewerActivity.EXTRA_CONTENT, terms.content)
        }
        startActivity(intent)
    }

    /**
     * Converte o tipo do documento em um título legível para o usuário.
     *
     * @param type Tipo do documento (ex.: "TERMS_OF_USE").
     * @return Título formatado (ex.: "Termos de Uso").
     */
    private fun resolveTitle(type: String): String = when (type) {
        "TERMS_OF_USE"   -> "Termos de Uso"
        "PRIVACY_POLICY" -> "Política de Privacidade"
        else             -> type.replace("_", " ")
    }
}