package com.nohana.echoes_app.view

import android.icu.text.CaseMap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.ForgotPasswordCodeScreen
import com.nohana.echoes_app.view.screen.ForgotPasswordEmailScreen
import com.nohana.echoes_app.view.screen.ForgotPasswordNewPasswordScreen
import com.nohana.echoes_app.view.screen.LoadingScreen
import com.nohana.echoes_app.view.state.ForgotPasswordState
import com.nohana.echoes_app.viewmodel.PasswordResetViewModel
import com.nohana.echoes_app.viewmodel.factory.PasswordResetViewModelFactory

class PasswordResetActivity(): ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: PasswordResetViewModel by viewModels {
            PasswordResetViewModelFactory(
                NetworkProvider.getAddress(applicationContext),
                applicationContext
            )
        }

        setContent {
            val state by viewModel.state.collectAsState()

            Column() {
                TitleComponent("Recuperação")

                when (val s = state) {
                    is ForgotPasswordState.Email -> ForgotPasswordEmailScreen(
                        onSendCode = viewModel::sendCode
                    )
                    is ForgotPasswordState.EmailValidationError -> ForgotPasswordEmailScreen(
                        onSendCode = viewModel::sendCode,
                        emailError = s.emailError
                    )
                    is ForgotPasswordState.EmailError -> ForgotPasswordEmailScreen(
                        onSendCode = viewModel::sendCode,
                        errorMessage = s.message
                    )
                    is ForgotPasswordState.Code -> ForgotPasswordCodeScreen(
                        email = s.email,
                        onValidateCode = { code -> viewModel.validateCode(s.email, code) }
                    )
                    is ForgotPasswordState.CodeValidationError -> ForgotPasswordCodeScreen(
                        email = s.email,
                        onValidateCode = { code -> viewModel.validateCode(s.email, code) },
                        codeError = s.codeError
                    )
                    is ForgotPasswordState.CodeError -> ForgotPasswordCodeScreen(
                        email = s.email,
                        onValidateCode = { code -> viewModel.validateCode(s.email, code) },
                        errorMessage = s.message
                    )
                    is ForgotPasswordState.NewPassword -> ForgotPasswordNewPasswordScreen(
                        onResetPassword = { newPass, confirmPass ->
                            viewModel.resetPassword(s.email, s.code, newPass, confirmPass)
                        }
                    )
                    is ForgotPasswordState.NewPasswordValidationError -> ForgotPasswordNewPasswordScreen(
                        onResetPassword = { newPass, confirmPass ->
                            viewModel.resetPassword(s.email, s.code, newPass, confirmPass)
                        },
                        newPasswordError = s.newPasswordError,
                        confirmPasswordError = s.confirmPasswordError
                    )
                    is ForgotPasswordState.NewPasswordError -> ForgotPasswordNewPasswordScreen(
                        onResetPassword = { newPass, confirmPass ->
                            viewModel.resetPassword(s.email, s.code, newPass, confirmPass)
                        },
                        errorMessage = s.message
                    )
                    is ForgotPasswordState.Loading -> LoadingScreen()
                    is ForgotPasswordState.Success -> LaunchedEffect(Unit) { finish() }
                }
            }

        }
    }
}