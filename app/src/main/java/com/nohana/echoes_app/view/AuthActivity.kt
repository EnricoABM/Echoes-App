package com.nohana.echoes_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.state.LoginState
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.LoadingScreen
import com.nohana.echoes_app.view.screen.LoginScreen
import com.nohana.echoes_app.view.screen.TwoFactorScreen
import com.nohana.echoes_app.view.state.LoginState.*
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory

class AuthActivity(): ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            NetworkProvider.getAddress(),
            applicationContext
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.loginState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.validateToken()
            }

            EchoesAppTheme {
                Column() {
                    TitleComponent("Login")

                    when(val s = state) {
                        LoginState.Login -> {
                            LoginScreen(
                                onLogin = viewModel::login
                            )
                        }
                        LoginState.Loading -> LoadingScreen()
                        is LoginState.Success -> {
                            LaunchedEffect(state) {
                                startActivity(
                                    Intent(this@AuthActivity, UserInfoActivity::class.java)
                                )
                                finish()
                            }
                        }

                        is LoginState.TwoFactor -> {
                            TwoFactorScreen(
                                s.email,
                                viewModel::sendTwoFactor,
                                s.error
                            )
                        }
                        LoginState.ValidToken -> {
                            LaunchedEffect(state) {
                                startActivity(
                                    Intent(this@AuthActivity, UserInfoActivity::class.java)
                                )
                                finish()
                            }
                        }

                        is LoginState.Error -> LoginScreen(
                            onLogin = viewModel::login,
                            s.error
                        )

                        is LoginState.ValidationError -> LoginScreen(
                            onLogin = viewModel::login,
                            emailError = s.emailError,
                            passwordError = s.passwordError
                        )
                    }
                }
            }
        }
    }
}