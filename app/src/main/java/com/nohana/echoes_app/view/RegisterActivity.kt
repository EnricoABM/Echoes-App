package com.nohana.echoes_app.view

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
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.LoadingScreen
import com.nohana.echoes_app.view.screen.RegisterScreen
import com.nohana.echoes_app.view.screen.ValidateCode
import com.nohana.echoes_app.view.state.RegisterState
import com.nohana.echoes_app.viewmodel.RegisterViewModel
import com.nohana.echoes_app.viewmodel.factory.RegisterViewModelFactory
import kotlin.getValue


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
                    RegisterState.Loading -> LoadingScreen()

                    RegisterState.Register -> RegisterScreen(
                        onRegister = viewModel::register
                    )
                    is RegisterState.RegisterValidationError -> RegisterScreen(
                        onRegister = viewModel::register,
                        nameError = s.nameError,
                        emailError = s.emailError,
                        passwordError = s.passwordError,
                        confirmPasswordError = s.confirmPasswordError
                    )
                    is RegisterState.RegisterError -> RegisterScreen(
                        onRegister = viewModel::register,
                        errorMessage = s.message
                    )

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

                    RegisterState.Success -> LaunchedEffect(Unit) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}