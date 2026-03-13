package com.nohana.echoes_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.view.state.LoginState
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.LoadingScreen
import com.nohana.echoes_app.view.screen.LoginScreen
import com.nohana.echoes_app.view.screen.TwoFactorScreen
import com.nohana.echoes_app.viewmodel.AuthViewModel

class AuthActivity(): ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: AuthViewModel by viewModels()

        setContent {
            val state by viewModel.loginState.collectAsState()

            EchoesAppTheme {
                Column() {
                    TitleComponent("Login")

                    Log.d("STATE", state.toString())

                    when(state) {
                        is LoginState.Login -> {
                            LoginScreen(
                                onLogin = viewModel::login,
                                (state as LoginState.Login).isUnauthorized
                            )
                        }
                        LoginState.Loading -> LoadingScreen()
                        is LoginState.Success -> {
                            startActivity(
                                Intent(baseContext, HomeActivity::class.java)
                            )
                        }

                        is LoginState.TwoFactor -> {
                            TwoFactorScreen(
                                (state as LoginState.TwoFactor).email,
                                viewModel::sendTwoFactor,
                                (state as LoginState.TwoFactor).isUnauthorized
                            )
                        }
                        LoginState.Unauthorized -> TODO()
                        LoginState.Error -> TODO()
                    }
                }
            }
        }
    }
}