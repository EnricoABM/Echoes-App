package com.nohana.echoes_app.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.nohana.echoes_app.MainActivity
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.*
import com.nohana.echoes_app.view.state.RegisterState
import com.nohana.echoes_app.viewmodel.RegisterViewModel
import com.nohana.echoes_app.viewmodel.factory.RegisterViewModelFactory

/**
 * Activity responsável pelo fluxo completo de registro de novos usuários.
 *
 * Observa o [RegisterViewModel.state] e renderiza a tela correspondente:
 *
 * - [RegisterState.Register] / erros → [RegisterScreen] com links para os termos
 * - [RegisterState.ViewTerms] → [TermsScreen] (somente leitura)
 * - [RegisterState.ViewTermsError] → [RegisterScreen] com mensagem de erro
 * - [RegisterState.ValidEmail] / erros de código → [ValidateCode]
 * - [RegisterState.Success] → navega para [MainActivity]
 */
class RegisterActivity : ComponentActivity() {

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            NetworkProvider.getAddress(applicationContext),
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState()

            Column {
                TitleComponent("Registro")

                when (val s = state) {

                    // ── Carregando ────────────────────────────────────────────
                    RegisterState.Loading -> LoadingScreen()

                    // ── Formulário de cadastro ────────────────────────────────────────────
                    RegisterState.Register -> RegisterScreen(
                        onRegister = viewModel::register,
                        onViewTerms = {
                            startActivity(Intent(this@RegisterActivity, TermsActivity::class.java))
                        }
                    )

                    is RegisterState.RegisterValidationError -> RegisterScreen(
                        onRegister = viewModel::register,
                        onViewTerms = {
                            startActivity(Intent(this@RegisterActivity, TermsActivity::class.java))
                        },
                        nameError = s.nameError,
                        emailError = s.emailError,
                        passwordError = s.passwordError,
                        confirmPasswordError = s.confirmPasswordError,
                        termsError = s.termsError
                    )

                    is RegisterState.RegisterError -> RegisterScreen(
                        onRegister = viewModel::register,
                        onViewTerms = {
                            startActivity(Intent(this@RegisterActivity, TermsActivity::class.java))
                        },
                        errorMessage = s.message
                    )

                    // ── Termos (tela unificada, somente leitura) ──────────────────────────
                    is RegisterState.ViewTerms -> TermsScreen(
                        termsOfUse = s.termsOfUse,
                        privacyPolicy = s.privacyPolicy,
                        onBack = viewModel::backToRegister
                    )

                    is RegisterState.ViewTermsError -> RegisterScreen(
                        onRegister = viewModel::register,
                        onViewTerms = viewModel::loadAllTermsForViewing,
                        errorMessage = s.message
                    )

                    // ── Validação 2FA ─────────────────────────────────────────
                    is RegisterState.ValidEmail -> ValidateCode(
                        email = s.email,
                        onValidate = viewModel::validateCode
                    )

                    is RegisterState.CodeValidationError -> ValidateCode(
                        email = s.email,
                        onValidate = viewModel::validateCode,
                        codeError = s.codeError
                    )

                    is RegisterState.CodeError -> ValidateCode(
                        email = s.email,
                        onValidate = viewModel::validateCode,
                        errorMessage = s.message
                    )

                    // ── Sucesso ───────────────────────────────────────────────
                    RegisterState.Success -> LaunchedEffect(Unit) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}