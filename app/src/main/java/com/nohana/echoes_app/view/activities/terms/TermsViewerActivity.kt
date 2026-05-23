package com.nohana.echoes_app.view.activities.terms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.nohana.echoes_app.view.components.TitleComponent

/**
 * Activity que exibe o conteúdo completo de um único termo em WebView.
 *
 * Recebe o título e o conteúdo HTML via Intent extras. Ao fechar,
 * retorna automaticamente para a [TermsActivity].
 */
class TermsViewerActivity : ComponentActivity() {

    companion object {
        /** Extra com o título do documento a ser exibido no cabeçalho. */
        const val EXTRA_TITLE = "extra_terms_title"

        /** Extra com o conteúdo HTML do documento a ser renderizado. */
        const val EXTRA_CONTENT = "extra_terms_content"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Termos"
        val content = intent.getStringExtra(EXTRA_CONTENT) ?: ""

        setContent {
            Column {
                TitleComponent(title)

                TermsViewerScreen(
                    content = content,
                    onClose = { finish() }
                )
            }
        }
    }
}