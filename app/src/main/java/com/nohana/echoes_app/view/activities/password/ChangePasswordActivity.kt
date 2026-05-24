package com.nohana.echoes_app.view.activities.password

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.components.LoadingScreen
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.viewmodel.ChangePasswordViewModel
import com.nohana.echoes_app.viewmodel.factory.ChangePasswordViewModelFactory

class ChangePasswordActivity : ComponentActivity() {

    private val viewModel: ChangePasswordViewModel by viewModels {
        ChangePasswordViewModelFactory(
            NetworkProvider.getAddress(),
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.state.collectAsState()

            EchoesAppTheme {
                Column {
                    TitleComponent(
                        "Alterar Senha",
                    )

                    when (val s = state) {
                        is ChangePasswordState.CurrentPassword -> {
                            ChangePasswordCurrentScreen(
                                onValidate = viewModel::validateCurrentPassword
                            )
                        }
                        is ChangePasswordState.NewPassword -> {
                            ChangePasswordNewScreen(
                                onChangePassword = { newPass, confirmPass ->
                                    viewModel.changePassword(newPass, confirmPass)
                                }
                            )
                        }
                        is ChangePasswordState.Loading -> LoadingScreen()
                        is ChangePasswordState.Success -> {
                            LaunchedEffect(Unit) {
                                finish()
                            }
                        }
                        // Erros da Conexão com o Servidor
                        is ChangePasswordState.Error -> {
                            ChangePasswordCurrentScreen(
                                onValidate = viewModel::validateCurrentPassword,
                                errorMessage = s.message
                            )
                        }
                        // Erros de Validação de Campos
                        is ChangePasswordState.CurrentPasswordValidationError -> ChangePasswordCurrentScreen(
                            onValidate = viewModel::validateCurrentPassword,
                            passwordError = s.passwordError
                        )

                        is ChangePasswordState.NewPasswordValidationError -> ChangePasswordNewScreen(
                            onChangePassword = viewModel::changePassword,
                            newPasswordError = s.newPasswordError,
                            confirmPasswordError = s.confirmPasswordError
                        )
                    }
                }
            }
        }
    }
}