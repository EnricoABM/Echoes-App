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
import androidx.compose.runtime.remember
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



        setContent {
            val viewModel = remember(this@AuthActivity) {
                AuthViewModel.create(
                    "http://192.168.15.77:8080/",
                    this@AuthActivity.applicationContext
                )
            }

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
                            LaunchedEffect(Unit) {
                                startActivity(
                                    Intent(baseContext, HomeActivity::class.java)
                                )
                            }
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