package com.nohana.echoes_app.view.activities.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.MainActivity
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.activities.terms.TermsActivity
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.components.LoadingScreen
import com.nohana.echoes_app.view.activities.terms.TermsScreen
import com.nohana.echoes_app.viewmodel.RegisterViewModel
import com.nohana.echoes_app.viewmodel.factory.RegisterViewModelFactory

class RegisterActivity : ComponentActivity() {

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            NetworkProvider.getAddress(),
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState()
            val form by viewModel.form.collectAsState()

            Column {
                TitleComponent("Registro")

                when (val s = state) {
                    RegisterState.Loading -> LoadingScreen()

                    RegisterState.Register -> RegisterScreen(
                        name = form.name,
                        email = form.email,
                        password = form.password,
                        confirmPassword = form.confirmPassword,
                        termsAccepted = form.termsAccepted,
                        onNameChange = { viewModel.onFormChange(name = it) },
                        onEmailChange = { viewModel.onFormChange(email = it) },
                        onPasswordChange = { viewModel.onFormChange(password = it) },
                        onConfirmPasswordChange = { viewModel.onFormChange(confirmPassword = it) },
                        onTermsAcceptedChange = { viewModel.onFormChange(termsAccepted = it) },
                        onRegister = viewModel::register,
                        onViewTerms = {
                            startActivity(Intent(this@RegisterActivity, TermsActivity::class.java))
                        }
                    )

                    is RegisterState.RegisterValidationError -> RegisterScreen(
                        name = form.name,
                        email = form.email,
                        password = form.password,
                        confirmPassword = form.confirmPassword,
                        termsAccepted = form.termsAccepted,
                        onNameChange = { viewModel.onFormChange(name = it) },
                        onEmailChange = { viewModel.onFormChange(email = it) },
                        onPasswordChange = { viewModel.onFormChange(password = it) },
                        onConfirmPasswordChange = { viewModel.onFormChange(confirmPassword = it) },
                        onTermsAcceptedChange = { viewModel.onFormChange(termsAccepted = it) },
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
                        name = form.name,
                        email = form.email,
                        password = form.password,
                        confirmPassword = form.confirmPassword,
                        termsAccepted = form.termsAccepted,
                        onNameChange = { viewModel.onFormChange(name = it) },
                        onEmailChange = { viewModel.onFormChange(email = it) },
                        onPasswordChange = { viewModel.onFormChange(password = it) },
                        onConfirmPasswordChange = { viewModel.onFormChange(confirmPassword = it) },
                        onTermsAcceptedChange = { viewModel.onFormChange(termsAccepted = it) },
                        onRegister = viewModel::register,
                        onViewTerms = {
                            startActivity(Intent(this@RegisterActivity, TermsActivity::class.java))
                        },
                        errorMessage = s.message
                    )

                    is RegisterState.ViewTerms -> TermsScreen(
                        termsOfUse = s.termsOfUse,
                        privacyPolicy = s.privacyPolicy,
                        onBack = viewModel::backToRegister
                    )

                    is RegisterState.ViewTermsError -> RegisterScreen(
                        name = form.name,
                        email = form.email,
                        password = form.password,
                        confirmPassword = form.confirmPassword,
                        termsAccepted = form.termsAccepted,
                        onNameChange = { viewModel.onFormChange(name = it) },
                        onEmailChange = { viewModel.onFormChange(email = it) },
                        onPasswordChange = { viewModel.onFormChange(password = it) },
                        onConfirmPasswordChange = { viewModel.onFormChange(confirmPassword = it) },
                        onTermsAcceptedChange = { viewModel.onFormChange(termsAccepted = it) },
                        onRegister = viewModel::register,
                        onViewTerms = viewModel::loadAllTermsForViewing,
                        errorMessage = s.message
                    )

                    is RegisterState.ValidEmail -> ValidateCode(
                        email = form.email,
                        code = form.code,
                        onCodeChange = { viewModel.onFormChange(code = it) },
                        onValidate = { code ->
                            viewModel.validateCode(form.email, code)
                        }
                    )

                    is RegisterState.CodeValidationError -> ValidateCode(
                        email = form.email,
                        code = form.code,
                        onCodeChange = { viewModel.onFormChange(code = it) },
                        onValidate = { code ->
                            viewModel.validateCode(form.email, code)
                        },
                        codeError = s.codeError
                    )

                    is RegisterState.CodeError -> ValidateCode(
                        email = form.email,
                        code = form.code,
                        onCodeChange = { viewModel.onFormChange(code = it) },
                        onValidate = { code ->
                            viewModel.validateCode(form.email, code)
                        },
                        errorMessage = s.message
                    )

                    RegisterState.Success -> LaunchedEffect(Unit) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}